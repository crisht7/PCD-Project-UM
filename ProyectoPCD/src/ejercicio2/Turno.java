package ejercicio2;

import java.util.concurrent.Semaphore;

public class Turno extends Thread {

	public static Semaphore peaton;
	public static Semaphore NorteSur;
	public static Semaphore EsteOeste;

	public static final int MAXNS = 4;
	public static final int MAXEO = 4;
	public static final int MAXP = 10;
	public static final int DEFAULT = 0;

	public Turno() {
		Turno.peaton = new Semaphore(Turno.DEFAULT);
		Turno.NorteSur = new Semaphore(Turno.DEFAULT);
		Turno.EsteOeste = new Semaphore(Turno.DEFAULT);
	}

	public void pasoNorteSur() {
		Turno.NorteSur = new Semaphore(Turno.MAXNS);

	}

	public void pasoEsteOeste() {
		Turno.EsteOeste = new Semaphore(Turno.MAXEO);
	}

	public void pasoPeatones() {
		Turno.peaton = new Semaphore(Turno.MAXP);
	}
	public void run() {
		/*
		 * Este método lo que hace es volver a crear los semaforos a unos con el numero de permisos igual
		 * al numero de coches o peatones que pueden  haber en la calzada
		 * cuando acaba el tiempo de un semaforo recoge todos sus permisos y lo cambia por uno  sin permisos disponibles
		 * y así mientras la variable global x no indique el final del programa
		 * */
		Vehiculo.generarVehiculo();
		//TODO lanzar peatones
		
		while(true) {
			try {
				System.out.println("Norte-Sur en verde");
				this.pasoNorteSur();
				Thread.sleep(500);
				System.out.println("Norte-Sur en rojo");
				Turno.NorteSur.drainPermits(); //resetea permisos
				Turno.NorteSur = new Semaphore(Turno.DEFAULT);
				System.out.println("Este-Oeste en verde");
				this.pasoEsteOeste();
				Thread.sleep(500);
				System.out.println("Este-Oeste en rojo");
				Turno.EsteOeste.drainPermits();
				Turno.EsteOeste = new Semaphore(Turno.DEFAULT);
				System.out.println("Peatones en verde");
				this.pasoPeatones();
				Thread.sleep(500);
				System.out.println("Peatones en rojo");
				Turno.peaton.drainPermits();
				Turno.peaton = new Semaphore(Turno.DEFAULT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
