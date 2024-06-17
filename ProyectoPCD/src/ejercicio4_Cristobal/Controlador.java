package ejercicio4_Cristobal;

import java.util.Random;

import messagepassing.MailBox;
import messagepassing.Selector;

public class Controlador extends Thread {

	private Selector s = null;
	private MailBox buzSolicitarCaja;
	private MailBox[] buzDevolverCaja;
	private MailBox liberarCajaAsignada;
	private MailBox imprimirPantalla;
	private MailBox liberaPantalla;
	private MailBox buzSolicitarPagar;
	private MailBox[] buzPagar;

	private char cajaAsignada;
	private boolean caja_a_ocupada;
	private boolean caja_b_ocupada;
	private boolean pantallaLibre;
	public int tiempo_pago;

	public Controlador(MailBox buzSolicitarCaja, MailBox[] dc, MailBox liberarCajaAsignada, MailBox imprimirPantalla,
			MailBox liberaPantalla, MailBox buzSolicitarPagar, MailBox[] pagar) {

		caja_a_ocupada = false;
		caja_b_ocupada = false;
		cajaAsignada = 'C';
		pantallaLibre = true;
		this.buzDevolverCaja = dc;
		this.buzSolicitarCaja = buzSolicitarCaja;
		this.liberarCajaAsignada = liberarCajaAsignada;
		this.imprimirPantalla = imprimirPantalla;
		this.liberaPantalla = liberaPantalla;
		this.buzSolicitarPagar = buzSolicitarPagar;
		this.buzPagar = pagar;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException ex) {
		}
		s = new Selector();
		s.addSelectable(buzSolicitarCaja, false);
		s.addSelectable(buzSolicitarPagar, false);
		s.addSelectable(liberarCajaAsignada, false);
		s.addSelectable(imprimirPantalla, false);
        s.addSelectable(liberaPantalla, false);
		buzSolicitarPagar.setGuardValue(!caja_a_ocupada || !caja_b_ocupada);

		while (true) {

			switch (s.selectOrBlock(true)) {
			case 1:
				int id = (int) buzSolicitarCaja.receive();
				Random r = new Random();
				tiempo_pago = r.nextInt(10) + 1;

				if (tiempo_pago > 5) {
					cajaAsignada = 'A';	
				} else {
					cajaAsignada = 'B';

				}
				buzDevolverCaja[id].send(cajaAsignada);

				break;

			case 2:
				String cajApagar = (String) buzSolicitarPagar.receive();
				int personaId = Integer.parseInt(cajApagar.substring(1));
				char caja = cajApagar.charAt(0);

				if (caja == 'A' && !caja_a_ocupada) {
					caja_a_ocupada = true;
					buzPagar[personaId].send(tiempo_pago);
				} else if (caja == 'B' && !caja_b_ocupada) {
					caja_b_ocupada = true;
					buzPagar[personaId].send(tiempo_pago);
				}

				break;

			case 3:
				char cajaLiberada = (char) liberarCajaAsignada.receive();
				setCajaLibre(cajaLiberada);
				break;

			case 4:
				imprimirPantalla.receive();
				pantallaLibre = false;
				imprimirPantalla.send("ok");
				break;
			case 5:
				liberaPantalla.receive();
				pantallaLibre = true;
				break;
			}

		}

	}

	public void setCajaOcupada(char caja) {
		if (caja == 'A')
			caja_a_ocupada = true;
		if (caja == 'B')
			caja_b_ocupada = true;
	}

	public void setCajaLibre(char caja) {
		if (caja == 'A')
			caja_a_ocupada = false;
		if (caja == 'B')
			caja_b_ocupada = false;
	}

}