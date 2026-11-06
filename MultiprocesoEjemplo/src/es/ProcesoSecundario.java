package es;

public class ProcesoSecundario {

	/**
	 * Эта класс симулирует процесс, который при попытке выполнения
	 * возвращает код 103 (тип ошибки).
	 */
	public static void main(String[] args) {
		System.out.println("Proceso secundario en marcha....");
        
        // Процесс что-то делает, но генерирует ошибку и возвращает код 103//
        System.exit(103);

	}

}
