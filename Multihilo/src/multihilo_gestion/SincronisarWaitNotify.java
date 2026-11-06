package multihilo_gestion;

public class SincronisarWaitNotify {

    /**
     * ОБЩИЙ РЕСУРС (Recurso Compartido)
     * Это объект, к которому обращаются оба потока. 
     * Именно здесь находятся методы synchronized с wait() и notify().
     */
    static class RecursoCompartido {
        private int valor;
        private boolean disponible = false; // Флаг: есть ли сейчас готовые данные?

        // Метод Производителя (Генератора)
        public synchronized void generar(int v) {
            try {
                // ПОКА данные еще не забрали (disponible == true), Производителю нельзя делать новые.
                while (disponible) {
                    wait(); // Производитель освобождает "замок" и засыпает, ожидая своей очереди.
                }
                
                // Производим новое значение
                valor = v;
                System.out.println("Productor generó el valor: " + valor);
                
                // Меняем флаг: данные готовы для Потребителя!
                disponible = true;
                
                // Будим поток Потребителя, который спал и ждал данных.
                notify(); 
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Метод Потребителя
        public synchronized int consumir() {
            try {
                // ПОКА данных нет (disponible == false), Потребителю нечего забирать.
                while (!disponible) {
                    wait(); // Потребитель освобождает "замок" и засыпает.
                }
                
                // Потребляем значение
                System.out.println("Consumidor consumió el valor: " + valor);
                System.out.println("-----------------------------------");
                
                // Меняем флаг: мы всё съели, место свободно!
                disponible = false;
                
                // Будим поток Производителя, чтобы он сделал новую порцию.
                notify(); 
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return valor;
        }
    }

    // ПОТОК 1: ПРОИЗВОДИТЕЛЬ
    static class HiloProductor extends Thread {
        private RecursoCompartido recurso;

        public HiloProductor(RecursoCompartido recurso) {
            this.recurso = recurso;
        }

        @Override
        public void run() {
            // Производим 5 значений по очереди
            for (int i = 1; i <= 5; i++) {
                recurso.generar(i);
                try {
                    Thread.sleep(1000); // Имитация времени на производство
                } catch (InterruptedException e) { }
            }
        }
    }

    // ПОТОК 2: ПОТРЕБИТЕЛЬ
    static class HiloConsumidor extends Thread {
        private RecursoCompartido recurso;

        public HiloConsumidor(RecursoCompartido recurso) {
            this.recurso = recurso;
        }

        @Override
        public void run() {
            // Пытаемся забрать 5 значений
            for (int i = 1; i <= 5; i++) {
                recurso.consumir();
            }
        }
    }


    // ГЛАВНЫЙ КЛАСС (MAIN)
    public static void main(String[] args) {
        // 1. Создаем ОДИН общий ресурс
        RecursoCompartido recurso = new RecursoCompartido();

        // 2. Создаем потоки и передаем им этот общий ресурс
        HiloProductor productor = new HiloProductor(recurso);
        HiloConsumidor consumidor = new HiloConsumidor(recurso);

        System.out.println("Iniciando la comunicación Wait-Notify...\n");
        
        // 3. Запускаем оба потока одновременно
        productor.start();
        consumidor.start();
    }
}