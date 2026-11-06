package ObjectInputOutput;

import java.io.Serializable;

//Чтобы объект можно было "превратить в байты" и отправить по сети, 
//класс ОБЯЗАТЕЛЬНО должен реализовывать интерфейс Serializable.
public class Persona implements Serializable {
	private String nombre;
	private int edad;

	public Persona(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}

	@Override
	public String toString() {
		return "Persona [Nombre=" + nombre + ", Edad=" + edad + "]";
	}
}