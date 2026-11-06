package sockets_archivos;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorArchivo {
    public static void main(String[] args) {
        // 1. Имя файла, который мы хотим отправить. 
        // Он должен лежать в папке твоего проекта Eclipse.
        String rutaArchivo = "Sockets.pptx"; 

        try (ServerSocket servidor = new ServerSocket(7000)) {
            System.out.println("Esperando cliente para enviar archivo...");
            
            // 2. Сервер замирает и ждет. Как только клиент стучится, создается socket.
            Socket cliente = servidor.accept();
            
            // 3. ТРУБА В СЕТЬ: через этот поток мы будем "выливать" данные клиенту.
            DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
            
            // 4. ТРУБА К ЖЕСТКОМУ ДИСКУ: через этот поток мы будем "всасывать" наш файл.
            File fichero = new File(rutaArchivo);
            FileInputStream fileInput = new FileInputStream(fichero);
            
            // 5. НАШЕ ВЕДРО (Буфер): массив на 4096 байт (4 КБ). 
            // Мы будем переносить файл частями по 4 КБ, чтобы не перегружать память компьютера.
            byte[] buffer = new byte[4096];
            
            // Переменная, которая запоминает, сколько байт мы реально зачерпнули в ведро.
            int bytesLeidos;
            
            System.out.println("Enviando archivo...");
            
            // 6. ГЛАВНЫЙ ЦИКЛ ПЕРЕДАЧИ
            // fileInput.read(buffer) черпает данные из файла в ведро. 
            // Если вода (данные) закончилась, метод возвращает -1.
            while ((bytesLeidos = fileInput.read(buffer)) != -1) {
                // Берем зачерпнутую воду и выливаем ее в сеть (salida).
                // Указываем: лить из buffer, начиная с позиции 0, ровно bytesLeidos байт.
                salida.write(buffer, 0, bytesLeidos);
            }
            
            System.out.println("Archivo enviado con éxito.");
            
            // 7. Закрываем краны, чтобы не было утечки памяти.
            fileInput.close();
            salida.close();
            cliente.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}