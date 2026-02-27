package com.uptc.uptc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UptcUserService {

    @Value("${directory.name:data}")
    private String dirName;

    @Value("${file.name:users.txt}")
    private String fileName;

    private Path filePath;

    // Se ejecuta cuando el servicio inicia
    @PostConstruct
    public void init() {

        if (fileName == null || dirName == null) {
            throw new RuntimeException("NO SE ESPECIFICO directory.name O file.name");

        } else {

            try {
                Path dirPath = Paths.get(dirName);

                // Crear carpeta si no existe
                if (!Files.exists(dirPath)) {
                    Files.createDirectories(dirPath);
                }

                filePath = dirPath.resolve(fileName);

                // Crear archivo si no existe
                if (!Files.exists(filePath)) {
                    Files.createFile(filePath);
                }

            } catch (IOException e) {
                throw new RuntimeException("Error inicializando almacenamiento", e);
            }

        }
    }

    // Guardar usuario
    // synchronized -> solo un hilo al mismo tiempo
    public synchronized UptcUser save(UptcUser user) {

        List<UptcUser> users = findAll();

        Long newId = 1L;

        if (!users.isEmpty()) {
            newId = users.get(users.size() - 1).getId() + 1;
        }

        user.setId(newId);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {

            String line = user.getId() + "," +
                    user.getName() + "," +
                    user.getEmail() + "," +
                    user.getPassword();

            writer.write(line);
            writer.newLine();

            return user;

        } catch (IOException e) {
            throw new RuntimeException("Error guardando usuario", e);
        }
    }

    // Obtener todos los usuarios
    public List<UptcUser> findAll() {

        List<UptcUser> users = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 4) {
                    UptcUser user = new UptcUser(
                            Long.parseLong(data[0]),
                            data[1],
                            data[2],
                            data[3]);

                    users.add(user);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error leyendo usuarios", e);
        }

        return users;
    }

}
