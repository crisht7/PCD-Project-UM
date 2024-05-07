package ejercicio1;
import java.util.Random;

/**
 * Esta clase representa el productor que se encarga de generar elementos y almacenarlos en un array compartido.
 * Implementa la interfaz Runnable para poder ser ejecutado en un hilo separado.
 */
public class Productor implements Runnable {
	@Override
	public void run() {
		Random random = new Random();
		for (int i = 0; i < Main.NUM_BLOQUES; i++) {
			for (int j = 0; j < Main.TAM_BLOQUE; j++) {
				Main.cerrojo.lock();
				try {
					if (j % 2 == 0) {//Nos aseguramos que los elementos se guarden en posiciones pares del array
						Main.array[i * Main.TAM_BLOQUE + j] = random.nextInt(100); // Números aleatorios entre 0 y 99
					} else { 
						Main.array[i * Main.TAM_BLOQUE + j] = random.nextInt(3) + 1; // Operaciones aleatorias entre 1 y 3
					}
					} finally{
						Main.cerrojo.unlock();
					}
			}
		}
	}
}