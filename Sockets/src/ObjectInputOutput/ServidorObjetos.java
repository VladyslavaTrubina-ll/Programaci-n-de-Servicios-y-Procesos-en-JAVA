package ObjectInputOutput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorObjetos {
    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(6000)) {
            System.out.println("Esperando cliente (Objetos)...");
            Socket cliente = servidor.accept();
            
            // Создаем поток для приема ОБЪЕКТОВ
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            
            // Читаем объект методом readObject() и приводим его к типу Persona
            Persona personaRecibida = (Persona) entrada.readObject();
            System.out.println("Servidor recibió: " + personaRecibida);
            
            // Закрываем потоки в правильном порядке[cite: 7]
            entrada.close();
            cliente.close();
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}