package ejercicio2;

public class Turno extends Thread {
	public void run() {
		while (true) {

			// Incrementamos el turno actual 
			Main.turnoActual = (Main.turnoActual + 1) % 3;
			
			try {
				Main.sMutex.acquire(); // Adquiere el semáforo mutex
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Verifica si es el turno de los peatones y no hay otros eventos ocurriendo
			if (Main.turnoActual == Main.TURNO_PEATON && Main.PeatonesEsperando > 0 && Main.nPeatones <= 0 && Main.nVehiculosEO <= 0 && Main.nVehiculosNS <= 0){ 
				Main.sPeatones.release(); 
			}
			// Verifica si es el turno de los vehículos en dirección norte-sur y no hay otros eventos ocurriendo
			else if (Main.turnoActual == Main.TURNO_NS && Main.VehiculosNSEsperando > 0 && Main.nPeatones <= 0 && Main.nVehiculosEO <= 0 & Main.nVehiculosNS <= 0){ 
				Main.sNS.release(); 
			}
			// Verifica si es el turno de los vehículos en dirección este-oeste y no hay otros eventos ocurriendo
			else if(Main.turnoActual == Main.TURNO_EO && Main.VehiculosEOEsperando > 0 && Main.nPeatones <= 0 && Main.nVehiculosEO <= 0 && Main.nVehiculosNS <= 0){ 
				Main.sEO.release(); 
			}else{ 
				Main.sMutex.release(); 
			}
			
			try {
				Main.sMutexPantalla.acquire(); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Main.imprimirPantalla(Main.turnoActual); 
			
			Main.sMutexPantalla.release(); 
			
			try {
				sleep(Main.DURACION_SEMAFORO); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
