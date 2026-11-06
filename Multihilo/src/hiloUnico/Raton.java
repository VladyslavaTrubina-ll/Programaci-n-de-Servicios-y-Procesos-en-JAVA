package hiloUnico;

/**
 * Класс Мышь (Raton).
 * В этом примере мы используем ТОЛЬКО ОДИН ПОТОК (hilo único) — главный поток программы (main).
 * Это классическое "последовательное" программирование.
 */
public class Raton {
	private String nombre = "";
	private int tiempo = 0;
	
	// Конструктор: задаем имя мыши и сколько времени (в секундах) она будет кушать
	public Raton(String nombre, int tiempo) {
		super();
		this.nombre = nombre;
		this.tiempo = tiempo;
	}
	
	// Метод, имитирующий процесс еды
	public void comer() {
		try {
			System.out.printf("El Raton %s empieza a comer %n", nombre);
			
			// Thread.sleep() ставит ТЕКУЩИЙ поток на паузу.
			// sleep() работает в миллисекундах. Поэтому мы умножаем секунды (tiempo) на 1000.
			// Если tiempo = 4, поток уснет на 4000 миллисекунд (4 секунды).
			Thread.sleep(tiempo * 1000); 
			
			System.out.printf("El Raton %s ha terminado de comer %n", nombre);
			
		} catch (InterruptedException e) {
			// Thread.sleep() всегда требует блока try-catch.
			// Это нужно на случай, если кто-то попытается "разбудить" или прервать спящий поток.
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// Создаем трех мышей:
		// raton1 будет кушать 4 секунды
		// raton2 будет кушать 2 секунды
		// raton3 будет кушать 5 секунд
		Raton raton1 = new Raton("1", 4);
		Raton raton2 = new Raton("2", 2);
		Raton raton3 = new Raton("3", 5);
		
		System.out.println("--- Inicio ---");
		
		// Поскольку поток всего один (главный), методы выполняются СТРОГО по очереди.
		// Программа "застрянет" на raton1.comer() на 4 секунды, и только потом пойдет дальше.
		raton1.comer();
		raton2.comer();
		raton3.comer();
		
		// Эта строка появится на экране ровно через 11 секунд (4 + 2 + 5) после запуска.
		System.out.printf("Todos los Ratones han comido%n");
	}
}