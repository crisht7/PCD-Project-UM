package ejercicio1;
import java.util.Random;

public class Productor implements Runnable {
	@Override
	public void run() {
		Random random = new Random();
		for (int i = 0; i < Main.NUM_BLOCKS; i++) {
			for (int j = 0; j < Main.TAM_BLOQUE; j++) {
				Main.cerrojo.lock();
				try {
					if (j % 2 == 0) {//Nos aseguramos que los elementos se guarden en posiciones pares del array
						Main.array[i * Main.TAM_BLOQUE + j] = random.nextInt(100); // Números aleatorios entre 0 y 99
					} else { //Consecuentemente con la condición del if, las operaciones se guardaran de manera alternada con los elementos en posiciones impares
						// Codificamos operaciones como enteros: 1 = suma, 2 = resta, 3 = multiplicación
						Main.array[i * Main.TAM_BLOQUE + j] = random.nextInt(3) + 1;
					}
					} finally{
						Main.cerrojo.unlock();
					}
			}
		}
	}
}