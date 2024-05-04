package Ej_Monitores;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import Pruebas.Ejercicio3;

class MonitorMesa extends Thread {
	private ReentrantLock l = new ReentrantLock();
	private int[] tiempo = new int[4]; // Tiempo de los clientes en mesa.
	private int[] nclientesEsperando = new int[4]; // Número de clientes esperando en cada una de las colas
	private Condition[] mesas = new Condition[4]; // Variable condition para cada una de las mesas, cada una tendrá la suya
	private boolean[] ocupada = new boolean[4]; // Variable que 

	public MonitorMesa() {
		for (int i = 0; i < mesas.length; i++) {
			tiempo[i] = 0;
			nclientesEsperando[i] = 0;
			mesas[i] = l.newCondition();
			ocupada[i] = false;
		}
		
	}

	public static int minimoTiempo(int... tiempoMesas) {
		int indice = 0;
		// Ponemos min -1 como que todavía no ha escogido ningún tiempo de ninguna mesa.
		int min = -1;
		for (int i = 0; i < tiempoMesas.length; i++)
			if (tiempoMesas[i] < min || min == -1) {
				min = tiempoMesas[i];
				indice = i;
			}
		return indice;
	}

	// Método: SOLICITAR MESA
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

	// Método Liberar Mesa
	
	// TODO: SE PUEDE MEJORAR? SE PUEDE VER ALGUNA MANERA PARA MEJORARLO?
	synchronized void liberarMesa(int y_mesa, int mesa) {
		// resume
		l.lock();
		mesas[mesa].signal();
		nclientesEsperando[mesa]--;
		tiempo[mesa] = tiempo[mesa] - y_mesa;
		l.unlock();
	}

	public int getTiempoMesa(int i) {
		return tiempo[i];
	}

}
