package ejercicio2;

/**
 * La clase Peaton representa a un peatón que cruza una calle controlada por un semáforo.
 * Extiende la clase Thread para poder ejecutarse en un hilo separado.
 */
public class Peaton extends Thread{
	@Override
	public void run() {
		while (true) {
			// Protocolo de entrada
			try {
				Main.sMutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//Si el peaton no puede cruzar por cualquier motivo, este esperará
			if(Main.turnoActual != Main.TURNO_PEATON || Main.nPeatones >= Main.MAX_PEATONES || Main.nVehiculosEO > 0 || Main.nVehiculosNS> 0){
				// Incrementa el número de peatones esperando
				Main.PeatonesEsperando++;
				Main.sMutex.release();
				try {
					// Espera la señal para cruzar
					Main.sPeatones.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Decrementa el número de peatones esperando
				Main.PeatonesEsperando--;
			}

			Main.nPeatones++;

			// Comprueba si hay más peatones esperando y no se ha alcanzado el número máximo de peatones cruzando
			if (Main.PeatonesEsperando > 0 && Main.nPeatones < Main.MAX_PEATONES) {
				Main.sPeatones.release();
			} else {
				// Libera el mutex si no hay más peatones que puedan cruzar
				Main.sMutex.release();
			}

			try {
				Main.sMutexPantalla.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Cruzando peatón.");

			Main.sMutexPantalla.release();

			try {
				// Espera a que los peatones termine de cruzar
				sleep(Main.T_CRUCE_PEATON);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				// Adquiere el mutex para actualizar el contador de peatones
				Main.sMutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Decrementa el número de peatones cruzando
			Main.nPeatones--;
			
			// Siguen peatones
			if (Main.turnoActual == Main.TURNO_PEATON && Main.PeatonesEsperando > 0 && Main.nPeatones < Main.MAX_PEATONES) { 
				Main.sPeatones.release(); 
			}
			// Turno vehículos N-S
			else if (Main.turnoActual == Main.TURNO_NS && Main.nPeatones <= 0 && Main.VehiculosNSEsperando > 0){ 
				Main.sPeatones.release(); 
				}

			// Turno vehículos E-O
			else if (Main.turnoActual == Main.TURNO_EO && Main.nPeatones <= 0 && Main.VehiculosEOEsperando > 0){ 
				Main.sEO.release(); 
				}else{ 
					Main.sMutex.release(); }
			try {
				sleep(Main.INTENTO_PEATON);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
