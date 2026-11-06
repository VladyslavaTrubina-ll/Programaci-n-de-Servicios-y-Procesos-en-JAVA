package multiples_clientes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// Класс должен наследоваться от Thread (или реализовывать Runnable)[cite: 7]
public class HiloCliente extends Thread {
    private Socket socketCliente;

    // Конструктор получает сокет конкретного клиента[cite: 7]
    public HiloCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    @Override
    public void run() {
        try {
            // Открываем потоки ввода/вывода для этого клиента
            DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
            DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream());
            
            // Читаем сообщение от клиента
            String mensaje = entrada.readUTF();
            System.out.println("Mensaje de " + socketCliente.getInetAddress() + ": " + mensaje);
            
            // Имитируем долгую работу (например, запрос к БД)
            Thread.sleep(2000); 
            
            // Отправляем ответ
            salida.writeUTF("Hola, tu peticion ha sido procesada en un hilo independiente.");
            
            // Обязательно закрываем соединения в конце[cite: 7]
            salida.close();
            entrada.close();
            socketCliente.close();
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}