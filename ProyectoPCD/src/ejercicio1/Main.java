package ejercicio1;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
    static final int NUM_CONSUMIDORES = 10;
    static final int TAM_ARRAY = 110;
    static final int TAM_BLOQUE = 11;
    static final int NUM_BLOQUES = TAM_ARRAY / TAM_BLOQUE;
    
    static final int[] array = new int[TAM_ARRAY];
    static final int[] solucion = new int[NUM_CONSUMIDORES];
    static final ReentrantLock cerrojo = new ReentrantLock();
    
    public static void main(String[] args) {
        Thread hiloProductor = new Thread(new Productor());
        hiloProductor.start();
        try {
			hiloProductor.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Thread[] hiloConsumidor = new Thread[NUM_CONSUMIDORES];
        for (int i = 0; i < NUM_CONSUMIDORES; i++) {
            hiloConsumidor[i] = new Thread(new Consumidor(i));
            hiloConsumidor[i].start();
        }

        Thread sumador = new Thread(new Sumador(solucion));

        try {
            hiloProductor.join();
            for (Thread hilosConsumidores : hiloConsumidor) {
                hilosConsumidores.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
        sumador.start();
        try {
			sumador.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    
}
