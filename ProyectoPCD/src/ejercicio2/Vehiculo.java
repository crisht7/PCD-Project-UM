package ejercicio2;
public class Vehiculo extends Thread{
	@Override
	public void run() {
		while (true) {
			
			try {
				Main.sMutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Comprueba si es el turno de los vehículos Norte-Sur y si se cumplen las condiciones para cruzar
			if (Main.turnoActual != Main.TURNO_NS || Main.nVehiculosNS >= Main.MAX_VEHICULO || Main.nPeatones > 0 || Main.nVehiculosEO > 0){
				Main.VehiculosNSEsperando++;
				Main.sMutex.release();
				try {
					Main.sNS.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Main.VehiculosNSEsperando--;
			}

			Main.nVehiculosNS++;
			
			// Si hay vehículos esperando y aún hay espacio para más vehículos, libera el semáforo para permitir el cruce
			if (Main.VehiculosNSEsperando > 0 && Main.nVehiculosNS < Main.MAX_VEHICULO) {
				Main.sNS.release();
			}else {
				Main.sMutex.release();
			}
			try {
				Main.sMutexPantalla.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Cruza vehículo Norte-Sur.");
			Main.sMutexPantalla.release();
			
			try {
				sleep(Main.T_CRUCE_VEHICULO);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				Main.sMutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Main.nVehiculosNS--;
			
			// Comprueba si es el turno de los vehículos Norte-Sur y si hay vehículos esperando o peatones cruzando
			if (Main.turnoActual==Main.TURNO_NS && Main.VehiculosNSEsperando > 0 && Main.nVehiculosNS < Main.MAX_VEHICULO){ 
				Main.sNS.release(); 
			}else if (Main.turnoActual == Main.TURNO_PEATON && Main.nVehiculosNS <= 0 && Main.PeatonesEsperando > 0){ 
				Main.sPeatones.release(); 
			}else if (Main.turnoActual == Main.TURNO_EO && Main.nVehiculosNS <= 0 && Main.VehiculosEOEsperando > 0){ 
				Main.sEO.release(); 
			}
			else{ 
				Main.sMutex.release(); 
			}
			
			try {
				sleep(Main.INTENTO_VEHICULO);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
			try {
				Main.sMutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Comprueba si es el turno de los vehículos Este-Oeste y si se cumplen las condiciones para cruzar
			if(Main.turnoActual != Main.TURNO_EO || Main.nVehiculosEO >= Main.MAX_VEHICULO || Main.nPeatones > 0 || Main.nVehiculosNS > 0){
				Main.VehiculosEOEsperando++;
				Main.sMutex.release();
				try {
					Main.sEO.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Main.VehiculosEOEsperando--;
			}
			
			Main.nVehiculosEO++;
			
			// Si hay vehículos esperando y aún hay espacio para más vehículos, libera el semáforo para permitir el cruce
			if (Main.VehiculosEOEsperando > 0 && Main.nVehiculosEO < Main.MAX_VEHICULO) {
				Main.sEO.release();
			}else {
				Main.sMutex.release();
			}

			try {
				Main.sMutexPantalla.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Cruza vehículo Este-Oeste.");
			Main.sMutexPantalla.release();
			
			try {
				sleep(Main.T_CRUCE_VEHICULO);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				Main.sMutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Main.nVehiculosEO--;
			
			// Comprueba si es el turno de los vehículos Este-Oeste y si hay vehículos esperando o si aún hay espacio para más vehículos
			if(Main.turnoActual == Main.TURNO_EO && Main.VehiculosEOEsperando > 0 && Main.nVehiculosEO < Main.MAX_VEHICULO){ 
				Main.sEO.release(); 
			}
			// Comprueba si es el turno de los peatones y si no hay vehículos Este-Oeste cruzando ni peatones esperando
			else if (Main.turnoActual == Main.TURNO_PEATON && Main.nVehiculosEO <= 0 && Main.PeatonesEsperando > 0){ 
				Main.sPeatones.release(); 
			}
			// Comprueba si es el turno de los vehículos Norte-Sur y si no hay vehículos Este-Oeste cruzando ni vehículos esperando
			else if (Main.turnoActual == Main.TURNO_NS && Main.nVehiculosEO <= 0 && Main.VehiculosNSEsperando > 0){ 
				Main.sNS.release(); 
			}
			// Si no se cumple ninguna de las condiciones anteriores, libera el mutex
			else{ 
				Main.sMutex.release(); 
			}
			try {
				sleep(Main.INTENTO_VEHICULO);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

