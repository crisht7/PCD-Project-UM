package ejercicio2;

import java.util.concurrent.Semaphore;



public class Main {

	//Constantes
	private static final int VEHICULOS_TOTALES = 50;
	private static final int PEATONES_TOTALES = 100;
	
	static final int TURNO_PEATON = 0;
	static final int TURNO_NS = 2;
	static final int TURNO_EO = 1;

	static final int MAX_PEATONES = 10;
	static final int MAX_VEHICULO = 4;

	static final int DURACION_SEMAFORO = 5000;
	
	static final int INTENTO_VEHICULO = 7000;
	static final int INTENTO_PEATON = 8000;

	static final int T_CRUCE_VEHICULO = 500;
	static final int T_CRUCE_PEATON = 3000;

	// Variables globales
	static Turno turno = new Turno();
	static Vehiculo vehiculos[] = new Vehiculo[VEHICULOS_TOTALES];
	static Peaton peatones[] = new Peaton[PEATONES_TOTALES];

	static int nPeatones, PeatonesEsperando = 0;
	static int nVehiculosEO, VehiculosEOEsperando = 0;
	static int nVehiculosNS, VehiculosNSEsperando = 0;
	static int turnoActual = 1;

	// Declaración de semáforos
	static Semaphore sMutex = new Semaphore(1);
	static Semaphore sMutexPantalla = new Semaphore(1);
	static Semaphore sPeatones = new Semaphore(0);
	static Semaphore sEO = new Semaphore(0);
	static Semaphore sNS = new Semaphore(0);

	// Método para escribir el turno actual en la consola
	public static void imprimirPantalla(int turno) {
		if (turno == TURNO_PEATON) {
			System.out.println("Semaforo peatones verde.");
		} else if (turno == TURNO_EO) {
			System.out.println("Semafoto Este-Oeste verde.");
		} else if (turno == TURNO_NS) {
			System.out.println("Semaforo Norte-sur verde.");
		}
	}

	public static void main(String[] args) {
		// Inicializamos el hilo de control de turnos, los hilos de vehiculos y los hilos de peatones
		turno.start();

		
		for (int i = 0; i < VEHICULOS_TOTALES; i++) {
			vehiculos[i] = new Vehiculo();
			vehiculos[i].start();
		}

		// Inicio de los hilos de los peatones
		for (int i = 0; i < PEATONES_TOTALES; i++) {
			peatones[i] = new Peaton();
			peatones[i].start();
		}

		try {
			// Espera a que el hilo de control de los turnos termine
			turno.join();

			// Espera a que todos los hilos de los vehículos terminen
			for (Vehiculo vehiculo : vehiculos)
				vehiculo.join();

			// Espera a que todos los hilos de los peatones terminen
			for (Peaton peaton : peatones)
				peaton.join();
		} catch (InterruptedException e) {
			System.err.println("Error");
		}
	}
}
