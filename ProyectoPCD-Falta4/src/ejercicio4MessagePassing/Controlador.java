package ejercicio4MessagePassing;

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
		buzSolicitarPagar.setGuardValue(!caja_a_ocupada || !caja_b_ocupada);

		while (true) {

			switch (s.selectOrBlock(true)) {
			case 1:
				int i = (int) buzSolicitarCaja.receive();
				Random r = new Random();
				tiempo_pago = r.nextInt(10) + 1;

				if (tiempo_pago > 5) {
					cajaAsignada = 'A';
					buzDevolverCaja[i].send(cajaAsignada);

				} else {
					cajaAsignada = 'B';
					buzDevolverCaja[i].send(cajaAsignada);

				}

				break;
			case 2:
				int cajApagar = (int) buzSolicitarPagar.receive();
				if (cajApagar == 'A' && !caja_a_ocupada) {
					caja_a_ocupada = true;
					/*String idString = cajApagar.substring(1, cajApagar.length());
					int id = Integer.parseInt(idString);
					*/buzPagar[id].send(tiempo_pago);
				}
				if (cajApagar == 'B' && !caja_b_ocupada) {
					caja_a_ocupada = true;
					/*String idString = cajApagar.substring(1, cajApagar.length());
					int id = Integer.parseInt(idString);
					*/
					buzPagar[id].send(tiempo_pago);
				*/}

				break;

			case 3:
				char caja = (char) liberarCajaAsignada.receive();
				setCajaLibre(caja);
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
