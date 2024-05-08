package ejercicio4MessagePassing;

import messagepassing.MailBox;

public class Main {
	

	
	public static void main(String[] args) {
		Persona[] personas = new Persona[30];
		MailBox sc = new MailBox(); // solicitaCaja
		MailBox dc = new MailBox(); // devuelveCaja
		MailBox lc = new MailBox(); // liberaCaja
		MailBox sp = new MailBox(); // solicitaPantalla
		MailBox lp = new MailBox(); // liberaPantalla
		MailBox solpagar = new MailBox(); // solicitaPagar
		MailBox pagar = new MailBox(); 
		
		Controlador control = new Controlador(sc, dc, lc, sp, lp, solpagar, pagar);
		for(int i = 0; i < personas.length; i++) {
			personas[i] = new Persona(i, control, sc, dc, lc, sp, lp, solpagar, pagar);
			personas[i].start();
		}
		control.start();

		for(int i = 0; i < personas.length; i++) {
			try {
				personas[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
