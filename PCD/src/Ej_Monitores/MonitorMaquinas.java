package Ej_Monitores;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorMaquinas extends Thread {
	private ReentrantLock l=new ReentrantLock();
	private int maquinas;
	private boolean[] libre = new boolean[3]; // Array de máquinas libres
	private int n; // Variable que llevará de por medio la última máquina libre
	private Condition maq = l.newCondition();

	public MonitorMaquinas() {
		maquinas = libre.length;
		for (int i = 0; i < libre.length; i++) {
			libre[i] = true;
		}
		n = 0;
	}

	 public int solicitarMaquina() throws InterruptedException {
		l.lock();
		if (maquinas <= 0) {
			maq.await();
		}
		while (!libre[n])
			n = (n +1) % libre.length ;
		libre[n] = false;
		maquinas--;
		l.unlock();
		return n;

	}

	public void liberarMaquina(int maquinaALiberar) throws InterruptedException {
		l.lock();
		libre[maquinaALiberar] = true;
		maquinas++;
		maq.signal(); // Resume de maq
		l.unlock();
	}
}
