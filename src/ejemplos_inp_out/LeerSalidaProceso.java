package ejemplos_inp_out;

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;

public class LeerSalidaProceso {
	
public static void main(String[] args) {
		
		// Запускаем просто командную строку, она ждет наших указаний
		ProcessBuilder pb = new ProcessBuilder("cmd");
		
		try {
			// В твоем примере была пропущена эта критически важная строчка запуска!
			Process p = pb.start(); 
			
			// 1. Получаем OutputStream (исходящий поток).
			// Через него мы отправляем данные ИЗ нашей Java-программы ВНУТРЬ процесса CMD.
			OutputStream os = p.getOutputStream();
			
			// Формируем команду. 
			// Символ \n в конце ОБЯЗАТЕЛЕН — он имитирует нажатие клавиши Enter!
			String dato = "echo Hola desde Java!\n";
			
			// Компьютер общается байтами, поэтому преобразуем строку в байты и отправляем
			os.write(dato.getBytes());
			
			// Метод flush() принудительно "проталкивает" зависшие данные по трубе прямо сейчас
			os.flush();
			
			// Закрываем канал связи
			os.close();
			
			System.out.println("Datos enviados al proceso correctamente.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
