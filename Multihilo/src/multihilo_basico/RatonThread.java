package multihilo_basico;

/**
 * СПОСОБ 1: Наследование от Thread.
 * Класс RatonThread получает все свойства и методы системного класса Thread.
 * Добавив "extends Thread", мы превратили обычный класс в "Независимого Рабочего".
 * Теперь каждый объект этого класса может выполнять свою задачу параллельно с другими.
 */
public class RatonThread extends Thread {
	private String nombre;
	private int tiempo;
	
	// Конструктор: здесь мы просто выдаем нашему Рабочему бейджик с именем
	// и говорим, сколько времени у него займет задача.
	public RatonThread(String nombre, int tiempo) {
		this.nombre = nombre;
		this.tiempo = tiempo;
	}
	
	// ПЕРЕОПРЕДЕЛЯЕМ МЕТОД run()
	// Это "сердце" потока. Всё, что написано здесь, будет выполняться параллельно.
	// будет делать ТОЛЬКО то, что написано внутри этого блока
	@Override
	public void run() {
		try {
			// Рабочий приступает к задаче
			System.out.printf("El Raton %s empieza a comer %n", nombre);
			
			// Спим указанное количество секунд
			// ВАЖНО: засыпает только этот конкретный Рабочий! Остальные продолжают работать.
			Thread.sleep(tiempo * 1000); 
			// Рабочий закончил свою задачу
			System.out.printf("El Raton %s ha terminado de comer %n", nombre);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// 1. Директор нанимает троих Рабочих (но они пока просто стоят и ничего не делают)
		RatonThread raton1 = new RatonThread("1 (Fievel)", 4);
		RatonThread raton2 = new RatonThread("2 (Jerry)", 2);
		RatonThread raton3 = new RatonThread("3 (Mickey)", 5);
		
		System.out.println("--- Comienza el festín (Thread) ---");
		
		// ОЧЕНЬ ВАЖНО: Мы вызываем start(), а НЕ run()!
		// Метод start() создает новый процесс в системе и сам невидимо вызывает run().
		// Если ты вызовешь run() напрямую, программа будет работать как один поток (по очереди).
		raton1.start();// Побежал первый рабочий (ему бежать 4 секунды)
		raton2.start();// МОМЕНТАЛЬНО следом побежал второй (ему бежать 2 секунды)
		raton3.start();// МОМЕНТАЛЬНО следом побежал третий (ему бежать 5 секунд)
		
		// Главный поток (main) дойдет до этой строчки мгновенно, не дожидаясь мышей рабочих.
		System.out.println("Todos los hilos han sido lanzados.");
	}
}