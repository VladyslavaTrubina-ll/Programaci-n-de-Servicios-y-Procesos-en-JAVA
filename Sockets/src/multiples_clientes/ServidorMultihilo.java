package multiples_clientes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorMultihilo {
    public static void main(String[] args) {
        int puerto = 8000;
        
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor Multihilo iniciado en puerto " + puerto);
            
            // Бесконечный цикл ожидания клиентов[cite: 7]
            while (true) {
                // Метод accept() ждет клиента. Как только он подключается, возвращается объект Socket[cite: 7]
                Socket cliente = servidor.accept();
                System.out.println("Nuevo cliente conectado: " + cliente.getInetAddress());
                
                // Создаем отдельный рабочий поток для этого клиента и передаем ему сокет[cite: 7]
                HiloCliente hilo = new HiloCliente(cliente);
                
                // Запускаем поток[cite: 7]. Главный цикл сразу идет на следующий круг ждать новых клиентов.
                hilo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
