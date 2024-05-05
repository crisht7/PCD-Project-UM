package ejercicio4;

import java.io.Serializable;
import java.util.Random;
import messagepassing.*;
import ejercicio4.Main;


public class Persona extends Thread {
	private int id;
	private int tiempo_pago;
	private char caja_asignada;
	private Controlador controlador;
	private MailBox buzSolicitarCaja;
	private MailBox buzDevolverCaja;
	private MailBox liberarCajaAsignada;
	private MailBox imprimirPantalla;
	private MailBox liberaPantalla;
	private MailBox buzSolicitarPagar;
	private MailBox	buzPagar;
	
	public Persona(int id, Controlador controlador,
			MailBox buzSolicitarCaja,
			MailBox buzDevolverCaja,
			MailBox liberarCajaAsignada,
			MailBox imprimirPantalla,
			MailBox liberaPantalla,
			MailBox buzSolicitarPagar,
			MailBox	buzPagar
			
			) {
		this.id = id;
		//TODO: tiempo_pago
		Random tiempo_aleatorio = new Random();
		this.tiempo_pago = tiempo_aleatorio.nextInt(10) + 1;
		this.caja_asignada = 'z';
		this.controlador = controlador;
		this.buzDevolverCaja = buzDevolverCaja;
		this.buzSolicitarCaja = buzSolicitarCaja;
		this.liberarCajaAsignada = liberarCajaAsignada;
		this.imprimirPantalla = imprimirPantalla;
		this.liberaPantalla = liberaPantalla;
		this.buzSolicitarPagar = buzSolicitarPagar;
		this.buzPagar = buzPagar;
		
	}
	
	private void imprimir_informacion() {

		System.out.println("Persona " + id + " ha usado la caja " + caja_asignada + "\nTiempo de pago: " + tiempo_pago);
	}
	
	public void run() {
		// Se ejecutará cinco veces por eso el 5
		Random tiempoAleatorio = new Random();
		int temp;
		for (int i = 0; i < 5; i++) {
			temp = tiempoAleatorio.nextInt(2000);
			try {
				Thread.sleep(temp);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			//2. Solicita ponerse en una caja
			buzSolicitarCaja.send(id);
			this.caja_asignada = (char) buzDevolverCaja.receive();
			
			//3. Realiza el pago en la caja
			buzSolicitarPagar.send(caja_asignada);
			this.tiempo_pago = (int) buzPagar.receive();
			
			try {
				Thread.sleep(tiempo_pago);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//4. libera la caja
			liberarCajaAsignada.send(caja_asignada);
			//5. imprime
			imprimirPantalla.send("");
			imprimirPantalla.receive();
			imprimir_informacion();
			liberaPantalla.send("");
		}
	}
	
}
