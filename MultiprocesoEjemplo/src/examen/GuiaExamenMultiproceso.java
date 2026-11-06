package examen;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * ГЛАВНАЯ ШПАРГАЛКА ПО MULTIPROCESO (JAVA)
 * Здесь собраны все основные методы работы с Process и ProcessBuilder.
 * Раскомментируй нужный метод в main(), чтобы протестировать его работу.
 */
public class GuiaExamenMultiproceso {

	public static void main(String[] args) {
		System.out.println("--- Inicio de la Guía de Examen ---");
		
		// На экзамене просто вызывай нужный метод из списка ниже:
		
		 ejecutarProcesoSimple();
		// leerSalidaDeProceso();
		// escribirEnProceso();
		// redirigirAArchivos();
		
		System.out.println("\n--- Fin del programa ---");
	}

	
	/**
	 * СЦЕНАРИЙ 1: Базовый запуск программы и ожидание ее завершения (waitFor, pid, exitValue)
	 * Идеально для запуска Notepad, калькулятора или другого Java-класса.
	 */
	public static void ejecutarProcesoSimple() {
		System.out.println("\n>>> EJECUTAR PROCESO SIMPLE <<<");
		try {
			// 1. Создаем "строителя". Передаем команду (например, запуск блокнота)
			ProcessBuilder pb = new ProcessBuilder("notepad");
			
			// ЕСЛИ НУЖНО ЗАПУСТИТЬ ДРУГОЙ JAVA КЛАСС (из папки bin), раскомментируй это:
			// ProcessBuilder pb = new ProcessBuilder("java", "paquete.MiClase");
			// pb.directory(new File("bin")); // Указываем рабочую папку
			// pb.inheritIO(); // Объединяем консоли, чтобы видеть System.out.println того класса
			
			// 2. Запускаем процесс в операционной системе
			Process proceso = pb.start();
			
			// 3. Получаем ID процесса в Windows (полезно для отладки)
			System.out.println("ID del proceso (PID): " + proceso.pid());
			
			// 4. Ждем завершения. Программа замирает здесь, пока блокнот не закроют!
			int codigoRetorno = proceso.waitFor();
			
			// 5. Проверяем статус завершения (0 = успех, всё остальное = ошибка)
			if (codigoRetorno == 0) {
				System.out.println("El proceso terminó con éxito.");
			} else {
				System.out.println("El proceso falló. Código: " + codigoRetorno);
			}
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * СЦЕНАРИЙ 2: Чтение данных из процесса (getInputStream)
	 * Идеально, когда нужно запустить системную команду (ping, dir, ipconfig) 
	 * и прочитать то, что она вывела, чтобы использовать эти данные в Java.
	 */
	public static void leerSalidaDeProceso() {
		System.out.println("\n>>> LEER SALIDA DEL PROCESO <<<");
		try {
			// Запускаем CMD, выполняем команду "ping google.com" и закрываем CMD (/c)
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "ping google.com");
			Process proceso = pb.start();
			
			// 1. Подключаемся к "выводу" процесса (берем то, что он печатает)
			InputStream is = proceso.getInputStream();
			
			// 2. Оборачиваем поток для удобного чтения построчно
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			String linea;
			System.out.println("Resultados del comando:");
			
			// 3. Читаем строку за строкой, пока процесс не прекратит вывод (null)
			while ((linea = reader.readLine()) != null) {
				System.out.println(" -> " + linea);
			}
			
			// 4. Обязательно закрываем поток
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * СЦЕНАРИЙ 3: Запись данных внутрь процесса (getOutputStream)
	 * Идеально, когда запущенная программа ждет ввода с клавиатуры от пользователя, 
	 * а мы хотим отправить ей команды автоматически прямо из кода Java.
	 */
	public static void escribirEnProceso() {
		System.out.println("\n>>> ESCRIBIR EN EL PROCESO <<<");
		try {
			// Запускаем CMD и оставляем его открытым (ждем команд)
			ProcessBuilder pb = new ProcessBuilder("cmd");
			Process proceso = pb.start();
			
			// 1. Подключаемся к "вводу" процесса (чтобы писать в него)
			OutputStream os = proceso.getOutputStream();
			
			// 2. Формируем команду. Символ \n ОБЯЗАТЕЛЕН (это нажатие Enter!)
			String comando = "echo Hola Profesor, he aprobado!\n";
			
			// 3. Переводим текст в байты и отправляем в процесс
			os.write(comando.getBytes());
			
			// 4. Проталкиваем данные (чтобы они не застряли в буфере)
			os.flush();
			
			// 5. Закрываем поток. Это даст понять CMD, что больше команд не будет
			os.close();
			
			System.out.println("Comando enviado exitosamente al CMD.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * СЦЕНАРИЙ 4: Перенаправление вывода в файлы (redirectOutput / redirectError)
	 * Идеально для автоматического создания логов. Заменяет ручное чтение через getInputStream.
	 */
	public static void redirigirAArchivos() {
		System.out.println("\n>>> REDIRIGIR SALIDA A ARCHIVOS <<<");
		try {
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "dir");
			
			// Указываем, куда сохранять обычный текст, который выводит процесс
			File archivoSalida = new File("salida_comando.txt");
			pb.redirectOutput(archivoSalida);
			
			// Указываем, куда сохранять ошибки (если, например, команда написана с опечаткой)
			File archivoErrores = new File("errores_comando.txt");
			pb.redirectError(archivoErrores);
			
			// Запускаем процесс. Текст в консоли Eclipse не появится, он весь уйдет в файлы!
			Process proceso = pb.start();
			proceso.waitFor();
			
			System.out.println("Revisa los archivos 'salida_comando.txt' y 'errores_comando.txt' en la carpeta del proyecto.");
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}