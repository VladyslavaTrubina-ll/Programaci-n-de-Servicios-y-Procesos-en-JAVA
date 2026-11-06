package sockets_archivos;

import java.io.*;
import java.net.Socket;

public class ClienteArchivo {
    public static void main(String[] args) {
        // 1. Имя файла, под которым мы сохраним скачанные данные.
        String archivoDestino = "recibido.pdf";

        // 2. Стучимся к серверу на порт 7000.
        try (Socket cliente = new Socket("localhost", 7000)) {
            
            // 3. ТРУБА ИЗ СЕТИ: через этот поток к нам будут приходить данные от сервера.
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            
            // 4. ТРУБА К ЖЕСТКОМУ ДИСКУ: через этот поток мы будем сохранять пришедшие данные.
            FileOutputStream fileOutput = new FileOutputStream(new File(archivoDestino));
            
            // 5. ВЕДРО КЛИЕНТА: оно должно быть такого же размера, как у сервера (4096 байт).
            byte[] buffer = new byte[4096];
            int bytesLeidos;
            
            System.out.println("Recibiendo archivo...");
            
            // 6. ГЛАВНЫЙ ЦИКЛ ПРИЕМА
            // entrada.read(buffer) ловит приходящие из сети данные в наше ведро.
            // Когда сервер закроет соединение, метод вернет -1.
            while ((bytesLeidos = entrada.read(buffer)) != -1) {
                // Выливаем пойманные данные из ведра на наш жесткий диск.
                fileOutput.write(buffer, 0, bytesLeidos);
            }
            
            System.out.println("Archivo guardado como: " + archivoDestino);
            
            // 7. Закрываем краны.
            fileOutput.close();
            entrada.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}