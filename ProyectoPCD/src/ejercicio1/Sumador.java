package ejercicio1;

/**
 * La clase Sumador implementa la interfaz Runnable y se utiliza para sumar los elementos de un array.
 */
public class Sumador implements Runnable{
	private int[] solucion;
	
	/**
	 * Constructor de la clase Sumador.
	 * @param solucion el array de enteros que se va a sumar.
	 */
	public Sumador(int[] solucion) {
		this.solucion = solucion;
	}
	
	/**
	 * Método run que realiza la suma de los elementos del array y muestra el resultado por consola.
	 */
	@Override
	public void run() {
		int sum = 0;
		for (int resultado : solucion) {
			sum += resultado;
		}
		System.out.println("Suma total: " + sum);
	}
}
