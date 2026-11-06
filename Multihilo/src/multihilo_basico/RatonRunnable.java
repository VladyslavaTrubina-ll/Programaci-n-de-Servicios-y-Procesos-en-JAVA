package multihilo_basico;

/**
 * СПОСОБ 2: Реализация интерфейса Runnable (implements Runnable).
 * Это ПРОФЕССИОНАЛЬНЫЙ и самый частый способ создания потоков в Java.
 * Почему? Потому что в Java можно наследоваться только от одного класса. 
 * Если твоя Мышь уже наследуется от класса Animal (extends Animal), 
 * ты не сможешь написать "extends Thread". А вот "implements Runnable" можно добавить всегда!
 */
public class RatonRunnable implements Runnable {
	private String nombre;
	private int tiempo;
	
	// Конструктор: здесь мы настраиваем саму ЗАДАЧУ (кому и сколько есть)
	public RatonRunnable(String nombre, int tiempo) {
		this.nombre = nombre;
		this.tiempo = tiempo;
	}
	
	// ПЕРЕОПРЕДЕЛЯЕМ МЕТОД run()
	// Как и в прошлом примере, это "Инструкция". 
	// Но сама по себе эта инструкция лежать и работать не будет, ей нужен исполнитель.
	@Override
	public void run() {
		try {
			System.out.printf("El Raton %s empieza a comer %n", nombre);
			
			// Текущий поток (тот рабочий, которому дадут эту инструкцию) засыпает
			Thread.sleep(tiempo * 1000); 
			
			System.out.printf("El Raton %s ha terminado de comer %n", nombre);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	// MAIN — ЭТО НАШ "ДИРЕКТОР" (Главный поток программы)
	public static void main(String[] args) {
		
		// 1. Директор пишет на бумаге ТРИ ИНСТРУКЦИИ (Задачи). 
		// Внимание: мы еще не создали потоки! Мы просто описали, что нужно сделать.
		RatonRunnable tarea1 = new RatonRunnable("1 (Fievel)", 4);
		RatonRunnable tarea2 = new RatonRunnable("2 (Jerry)", 2);
		RatonRunnable tarea3 = new RatonRunnable("3 (Mickey)", 5);
		
		System.out.println("--- Comienza el festín (Runnable) ---");
		
		// 2. НАНИМАЕМ РАБОЧИХ И ДАЕМ ИМ ИНСТРУКЦИИ.
		// Это ГЛАВНОЕ ОТЛИЧИЕ от первого способа. 
		// Мы создаем системные объекты Thread (Рабочих) и внутрь (в скобки) кладем нашу задачу.
		Thread trabajador1 = new Thread(tarea1);
		Thread trabajador2 = new Thread(tarea2);
		Thread trabajador3 = new Thread(tarea3);
		
		// 3. Директор дает команду "На старт!"
		// Мы вызываем метод start() именно у Рабочих (Thread), а не у задач (Runnable).
		trabajador1.start(); // Рабочий 1 берет задачу 1 и начинает ее выполнять параллельно
		trabajador2.start(); // Рабочий 2 берет задачу 2 и бежит параллельно
		trabajador3.start(); // Рабочий 3 берет задачу 3 и бежит параллельно
		
		// 4. Директор свободен.
		System.out.println("Todos los hilos han sido lanzados.");
	}
}
