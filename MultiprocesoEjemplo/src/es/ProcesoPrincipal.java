package es;

import java.io.File;

/**
 * Этот класс "Главный процесс" (Proceso Principal).
 * Его задача: запустить другую Java программу (второстепенный процесс),
 * дождаться ее завершения и проанализировать результат (код возврата).
 */
public class ProcesoPrincipal {
	public static void main(String[] args) {
		System.out.println("Ejecutamos el proceso secundario....");
		
		try {
			// 1. ПОДГОТОВКА ПРОЦЕССА (ProcessBuilder)
			// Мы создаем "строителя" процессов (ProcessBuilder). 
			// "java" - это команда операционной системе запустить виртуальную машину Java.
			// "es.ProcesoSecundario" - это полное имя класса (пакет + класс), который мы запускаем.
			ProcessBuilder builder = new ProcessBuilder("java", "es.ProcesoSecundario");
			
			// 2. УКАЗАНИЕ РАБОЧЕЙ ДИРЕКТОРИИ
			// Eclipse берет твой код из папки "src", компилирует его и прячет готовые файлы в папку "bin".
			// Если не указать папку "bin", Java будет искать класс в корне проекта и выдаст ошибку "Class not found".
			builder.directory(new File("bin"));
			
			// 3. ПЕРЕНАПРАВЛЕНИЕ ВВОДА/ВЫВОДА (СЕКРЕТНАЯ ДЕТАЛЬ)
			// По умолчанию, у нового процесса есть своя "невидимая" консоль. 
			// Метод inheritIO() приказывает: "всё, что второй процесс пишет в консоль, перенаправляй в мою консоль".
			// Благодаря этому мы увидим фразу "Proceso secundario en marcha....".
			builder.inheritIO(); 
			
			// 4. ЗАПУСК ПРОЦЕССА В ОС
			// До этой строчки мы просто "настраивали" процесс. Метод start() 
			// реально обращается к Windows/Linux/macOS и просит выделить ресурсы на новую программу.
			Process process = builder.start();
			
			// 5. ОЖИДАНИЕ ЗАВЕРШЕНИЯ И СИНХРОНИЗАЦИЯ (ОЧЕНЬ ВАЖНО)
			// Метод waitFor() ставит НАШ (главный) процесс на паузу ("блокирует" его).
			// Мы будем стоять на этой строчке и ждать, пока второй процесс не выполнит свой System.exit(103).
			// Как только он завершится, waitFor() вернет "код выхода" (exit code) той программы в переменную valorRetorno.
			int valorRetorno = process.waitFor();
			
			System.out.println("----------------------------------------");
			
			// 6. АНАЛИЗ РЕЗУЛЬТАТА
			// В операционных системах есть стандарт: код 0 означает, что всё прошло идеально (Success).
			// Любое число кроме нуля — это код какой-то ошибки (мы сами задали 103).
			if (valorRetorno == 0) {
				// Если вернулся 0 - радуемся
				System.out.println("Proceso secundario finalizado con éxito.");
			} else {
				// Если вернулось не 0 (в нашем случае придет 103) - обрабатываем ошибку
				System.out.println("El proceso secundario ha fallado.");
				System.out.println("Código de error: " + valorRetorno);
			}
			
		} catch (Exception e) {
			// Этот блок сработает, если, например, на компьютере не установлена Java 
			// или мы запретили создание процессов в настройках безопасности ОС.
			System.err.println("Se produjo un error al ejecutar el proceso.");
			e.printStackTrace();
		}
	}
}