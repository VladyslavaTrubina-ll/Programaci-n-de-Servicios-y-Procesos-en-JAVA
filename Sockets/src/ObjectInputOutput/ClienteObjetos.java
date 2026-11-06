package ObjectInputOutput;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteObjetos {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 6000;

        try (Socket cliente = new Socket(host, puerto)) {
            // Создаем поток для отправки ОБЪЕКТОВ
            ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
            
            // Создаем наш объект
            Persona persona = new Persona("Ana", 25);
            System.out.println("Enviando objeto: " + persona);
            
            // Отправляем объект на сервер с помощью writeObject()
            salida.writeObject(persona);
            
            salida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
