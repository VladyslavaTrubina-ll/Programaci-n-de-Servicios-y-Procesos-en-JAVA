package multihilo_gestion;

public class PrioridadesHilos {

    static class HiloPrioridad extends Thread {
        public HiloPrioridad(String nombre) {
            super(nombre); // Задаем имя потоку
        }

        @Override
        public void run() {
            // getPriority() возвращает текущий приоритет потока
            System.out.println("Soy el hilo " + getName() + " con prioridad " + getPriority());
            for (int i = 1; i <= 3; i++) {
                System.out.println(getName() + " ejecutando paso " + i);
            }
        }
    }

    public static void main(String[] args) {
        HiloPrioridad hiloMin = new HiloPrioridad("HILO_LENTO");
        HiloPrioridad hiloMax = new HiloPrioridad("HILO_RAPIDO");
        HiloPrioridad hiloNorm = new HiloPrioridad("HILO_NORMAL");

        // Устанавливаем приоритеты (от 1 до 10)
        hiloMin.setPriority(Thread.MIN_PRIORITY);  // 1
        hiloMax.setPriority(Thread.MAX_PRIORITY);  // 10
        hiloNorm.setPriority(Thread.NORM_PRIORITY); // 5 (по умолчанию)

        // Запускаем все потоки
        hiloMin.start();
        hiloMax.start();
        hiloNorm.start();
    }
}
