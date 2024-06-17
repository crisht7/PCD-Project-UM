package ejercicio3Monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * La clase MonitorMaquina representa un monitor que controla 
 * el acceso a un conjunto de máquinas.
 * Permite solicitar y liberar máquinas de forma concurrente.
 */
public class MonitorMaquina extends Thread {
	private ReentrantLock l = new ReentrantLock();
	private int maquinas;
	private boolean[] libre = new boolean[3]; // Array de máquinas libres
	private int n; // Variable que llevará de por medio la última máquina libre
	private Condition maq = l.newCondition();

	/**
	 * Crea un nuevo MonitorMaquina.
	 * Inicializa el número de máquinas y marca todas como libres.
	 */
	public MonitorMaquina() {
		maquinas = libre.length;
		for (int i = 0; i < libre.length; i++) {
			libre[i] = true;
		}
		n = 0;
	}

	/**
	 * Solicita una máquina disponible.
	 * Si no hay máquinas disponibles, el hilo se bloquea hasta que se libere una máquina.
	 * @return El índice de la máquina solicitada.
	 * @throws InterruptedException si el hilo es interrumpido mientras está esperando.
	 */
	public int solicitarMaquina() throws InterruptedException {
		l.lock();
		if (maquinas <= 0) {
			maq.await();
		}
		while (!libre[n])
			n = (n + 1) % libre.length;
		libre[n] = false;
		maquinas--;
		l.unlock();
		return n;
	}

	/**
	 * Libera una máquina previamente solicitada.
	 * @param maquinaALiberar El índice de la máquina a liberar.
	 * @throws InterruptedException si el hilo es interrumpido mientras está esperando.
	 */
	public void liberarMaquina(int maquinaALiberar) throws InterruptedException {
		l.lock();
		libre[maquinaALiberar] = true;
		maquinas++;
		maq.signal(); // Resume de maq
		l.unlock();
	}
}