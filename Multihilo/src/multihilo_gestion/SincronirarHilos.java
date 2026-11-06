package multihilo_gestion;

public class SincronirarHilos {

    // Класс банковского счета (общий ресурс)
    static class Cuenta {
        private int saldo = 100;
        
        // Ключевое слово 'volatile' означает, что эта переменная общая для всех потоков.
        // Она всегда читается напрямую из главной памяти, а не из кэша потока.
        // Полезно для флагов состояния (например, открыт/закрыт счет).
        public volatile boolean cuentaActiva = true;

        /*
         * OPCIÓN A: SIN SINCRONIZAR
         * Si usas este método (borrando la palabra synchronized), 
         * dos hilos pueden retirar dinero al mismo tiempo y dejar el saldo en negativo.
         */
        // public void retirarDinero(int cant, String nom) { ... }
        
        /*
         * OPCIÓN B: MÉTODO SINCRONIZADO (Recomendado)
         * Ключевое слово 'synchronized' вешает "замок" на этот метод.
         * Только ОДИН поток может выполнять этот метод в любой момент времени.
         * Если второй поток попытается зайти, он будет ждать, пока первый не выйдет.
         */
        public synchronized void retirarDinero(int cant, String nom) {
            if (!cuentaActiva) {
                System.out.println(nom + ": La cuenta está inactiva.");
                return;
            }

            if (saldo >= cant) {
                System.out.println(nom + ": Va a retirar " + cant + ". Saldo actual: " + saldo);
                try {
                    // Имитируем задержку операции (чтобы спровоцировать ошибку, если нет синхронизации)
                    Thread.sleep(500); 
                } catch (InterruptedException e) { }
                
                saldo -= cant;
                System.out.println(nom + ": Retiro exitoso. Nuevo saldo: " + saldo);
            } else {
                System.out.println(nom + ": No hay suficiente dinero para retirar " + cant + ". Saldo: " + saldo);
            }
        }
    }

    // Класс человека у банкомата (Поток)
    static class PersonaCajero extends Thread {
        private Cuenta cuenta;
        private int cantidadRetiro;

        public PersonaCajero(String nombre, Cuenta cuenta, int cantidad) {
            super(nombre);
            this.cuenta = cuenta;
            this.cantidadRetiro = cantidad;
        }

        @Override
        public void run() {
            // Поток вызывает общий метод счета
            cuenta.retirarDinero(cantidadRetiro, getName());
        }
    }

    public static void main(String[] args) {
        // Создаем ОДИН общий счет
        Cuenta cuentaCompartida = new Cuenta();

        // Создаем двух людей (потоки), которые пытаются снять деньги с ОДНОГО счета
        PersonaCajero persona1 = new PersonaCajero("Juan", cuentaCompartida, 80);
        PersonaCajero persona2 = new PersonaCajero("María", cuentaCompartida, 80);

        // Запускаем их одновременно
        persona1.start();
        persona2.start();
        
        // РЕЗУЛЬТАТ (Opción B):
        // Juan проверит баланс (100) и снимет 80. Баланс станет 20.
        // María будет ждать. Когда Juan закончит, она проверит баланс (20) и ей будет отказано.
        // Если убрать 'synchronized' (Opción A), они оба увидят баланс 100, оба снимут по 80, и баланс станет -60 (ошибка!).
    }
}