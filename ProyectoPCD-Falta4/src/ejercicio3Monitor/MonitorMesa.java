package ejercicio3Monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * La clase MonitorMesa representa un monitor que controla 
 * el acceso a las mesas de un restaurante.
 * Cada mesa tiene un tiempo asociado que indica 
 * la duración promedio de los clientes en esa mesa.
 * El monitor permite solicitar una mesa y liberar una mesa.
 */
public class MonitorMesa extends Thread {
	private ReentrantLock l = new ReentrantLock();
	private int[] tiempo = new int[4]; // Tiempo de los clientes en mesa.
	private int[] nclientesEsperando = new int[4]; // Número de clientes esperando en cada una de las colas
	private Condition[] mesas = new Condition[4]; // Variable condition para cada una de las mesas, cada una tendrá la suya
	private boolean[] ocupada = new boolean[4]; // Variable que indica si una mesa está ocupada o no

	/**
	 * Constructor de la clase MonitorMesa.
	 * Inicializa los array de tiempo, nclientesEsperando, mesas y ocupada.
	 */
	public MonitorMesa() {
		for (int i = 0; i < mesas.length; i++) {
			tiempo[i] = 0;
			nclientesEsperando[i] = 0;
			mesas[i] = l.newCondition();
			ocupada[i] = false;
		}
	}

	/**
	 * Método que encuentra el índice de la mesa con el tiempo mínimo.
	 * @param tiempo Los tiempos de las mesas.
	 * @return El índice de la mesa con el tiempo mínimo.
	 */
	public static int minimoTiempo(int... tiempo) {
		int indice = 0;
		// Ponemos min -1 como que todavía no ha escogido ningún tiempo de ninguna mesa.
		int min = -1;
		for (int i = 0; i < tiempo.length; i++)
			if (tiempo[i] < min || min == -1) {
				min = tiempo[i];
				indice = i;
			}
		return indice;
	}

	/**
	 * Método para solicitar una mesa.
	 * @param y_mesa El tiempo que el cliente estará en la mesa.
	 * @return El índice de la mesa asignada.
	 * @throws InterruptedException Si ocurre una interrupción mientras se espera por una mesa disponible.
	 */
	synchronized int solicitarMesa(int y_mesa) throws InterruptedException {
		l.lock();
		int indiceColaMasCorta = minimoTiempo(this.tiempo);
		try {
			nclientesEsperando[indiceColaMasCorta]++;
			tiempo[indiceColaMasCorta] = tiempo[indiceColaMasCorta] + y_mesa;
			// delay
			if (ocupada[indiceColaMasCorta]) {
				mesas[indiceColaMasCorta].await();
			}
		} finally {
			l.unlock();
		}
		return indiceColaMasCorta;
	}

	/**
	 * Método para liberar una mesa.
	 * @param y_mesa El tiempo que el cliente estuvo en la mesa.
	 * @param mesa El índice de la mesa a liberar.
	 */
	synchronized void liberarMesa(int y_mesa, int mesa) {
		l.lock();
		mesas[mesa].signal();
		nclientesEsperando[mesa]--;
		tiempo[mesa] = tiempo[mesa] - y_mesa;
		l.unlock();
	}

	/**
	 * Método para obtener el tiempo de una mesa específica.
	 * @param i El índice de la mesa.
	 * @return El tiempo de la mesa.
	 */
	public int getTiempoMesa(int i) {
		return tiempo[i];
	}
}