package programas_windows;

import java.io.IOException;

public class AbrirNotepad {
	public static void main(String[] args) {
		System.out.println("Vamos a abrir el Notepad");
		
		try {
			// Создаем настройку для запуска Блокнота Windows
			// создаем объект ProcessBuilder и сразу передаем ему команду, которую хотим выполнить (запуск блокнота).
			ProcessBuilder pb = new ProcessBuilder("notepad");
			
			// Метод start() обращается к операционной системе и запускает программу.
			Process proceso = pb.start();
			// 3. ПОЛУЧЕНИЕ ID ПРОЦЕССА
			// pid() возвращает уникальный идентификатор процесса (Process ID) в системе Windows/Linux.
			// Это тот самый номер, который ты видишь в "Диспетчере задач".
			System.out.println("El ID del proceso es " + proceso.pid());
			
			// Наша Java-программа останавливается на этой строчке.
			// Пока ты не закроешь открывшееся окно Блокнота, код дальше не пойдет.
			int codigoRetorno = proceso.waitFor();
			
			// Когда ты закроешь блокнот, программа продолжит работу и выведет код завершения (обычно 0).
			System.out.println("Fin del Proceso con el codigo " + codigoRetorno);
			
		} catch (IOException | InterruptedException e) {
			// Перехватываем ошибки: например, если написать "notepadddd", система не найдет программу.
			e.printStackTrace();
		}
	}
}
