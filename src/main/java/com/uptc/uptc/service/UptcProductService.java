package com.uptc.uptc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.uptc.uptc.model.UptcProduct;

import jakarta.annotation.PostConstruct;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UptcProductService {
    
    @Value("${directory.name:data}")
    private String dirName;

    @Value("${file.product.name:products.txt}")
    private String fileName;

    private Path filePath;

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
    public synchronized UptcProduct save(UptcProduct product) {

        List<UptcProduct> users = findAll();

        Long newId = 1L;

        if (!users.isEmpty()) {
            newId = users.get(users.size() - 1).getId() + 1;
        }

        product.setId(newId);

        try (
            
            BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {

            String line = product.getId() + "," +
                    product.getName() + "," +
                    product.getUnitMeasurement() + "," +
                    product.getPassword();

            writer.write(line);
            writer.newLine();

            return product;

        } catch (IOException e) {
            throw new RuntimeException("Error guardando usuario", e);
        }
    }

    // Obtener todos los usuarios
    public List<UptcProduct> findAll() {

        List<UptcProduct> products = new ArrayList<>();

        try (
            
            BufferedReader reader = Files.newBufferedReader(filePath)) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 4) {
                    UptcProduct product = new UptcProduct(
                            Long.parseLong(data[0]),
                            data[1],
                            UptcProduct.UnitMeasurement.valueOf(data[2]),
                            data[3]);

                    products.add(product);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error leyendo usuarios", e);
        }

        return products;
    }

}
