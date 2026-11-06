package multihilo_gestion;

public class EstadoHilos {

    // Вложенный класс потока
    static class MiHilo extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("Hilo: Empiezo a trabajar...");
                // Поток работает в цикле, пока его не прервут
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Hilo: Trabajando...");
                    Thread.sleep(1000); // Спим 1 секунду
                }
            } catch (InterruptedException e) {
                // Когда вызывается interrupt() во время sleep(), выбрасывается это исключение!
                // Это знак для потока, что нужно завершить работу.
                System.out.println("Hilo: ¡Me han interrumpido! Terminando mi trabajo.");
            }
        }
    }

    public static void main(String[] args) {
        MiHilo hilo = new MiHilo();
        hilo.start(); // Запускаем поток

        try {
            Thread.sleep(3500); // Главный поток (main) ждет 3.5 секунды
            
            // Проверяем, жив ли еще поток
            System.out.println("Main: ¿El hilo está vivo? " + hilo.isAlive());
            
            System.out.println("Main: Voy a interrumpir el hilo.");
            hilo.interrupt(); // Отправляем сигнал прерывания
            
            // join() заставляет главный поток ждать, пока 'hilo' полностью не завершится
            hilo.join(); 
            
            System.out.println("Main: El hilo ha terminado. ¿Sigue vivo? " + hilo.isAlive());
            System.out.println("Main: Fin del programa.");
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}