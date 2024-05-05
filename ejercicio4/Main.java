package ejercicio4;

import messagepassing.MailBox;

public class Main {
	

	
	public static void main(String[] args) {
		Persona[] personas = new Persona[30];
		MailBox sc = new MailBox(); // sc solicitaCaja
		MailBox dc = new MailBox(); // devuelveCaja
		MailBox lc = new MailBox(); // liberaCaja
		MailBox sp = new MailBox(); // solicitaPantalla
		MailBox lp = new MailBox(); // liberaPantalla
		MailBox solpagar = new MailBox(); // SolicitaPagar
		MailBox pagar = new MailBox(); // Pagar
		
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
