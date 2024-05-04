package Ej_Monitores;

import pr3.MonitorMensaje;
import java.util.Random;

public class MonitorCliente {

	public class Cliente extends Thread {
		private MonitorMaquinas maquina;
		private int x_maquina;
		private int y_mesa;
		private MonitorMesa mesa;
		private int id;

		// Constructor de cliente
		public Cliente(MonitorMaquinas maquina, MonitorMesa mesa, int id) {
			this.mesa = mesa;
			this.maquina = maquina;
			this.id = id;
			Random rand = new Random();
			Random rand2 = new Random();
			this.x_maquina = rand.nextInt(1000);
			this.y_mesa = rand2.nextInt(1000);
		}

		synchronized public void mostrarPorPantalla(int maquinaSolicitada, int mesaSolicitada) {
			/*
			 * 
			 * “Cliente id ha solicitado su servicio en la máquina: ----- Tiempo en
			 * solicitar el servicio: X Será atendido en la mesa: ----- Tiempo en la mesa =
			 * Y Tiempo de espera en la mesa1=----, mesa2=-----, mesa3=-----, mesa4=-----”
			 * 
			 */
			System.out.println("Cliente " + this.id + " ha solicitado su servicio en la máquina " + maquinaSolicitada);
			System.out.println("Tiempo en solicitar el servicio: " + this.x_maquina);
			System.out.println("Será atendido en la mesa: " + mesaSolicitada);
			System.out.println("Tiempo en la mesa = " + this.y_mesa);
			int mesa1 = mesa.getTiempoMesa(0);
			int mesa2 = mesa.getTiempoMesa(1);
			int mesa3 = mesa.getTiempoMesa(2);
			int mesa4 = mesa.getTiempoMesa(3);
			System.out.println("Y tiempo de espera en la mesa1= " + mesa1 + ", mesa2= " + mesa2 + ", mesa3= " + mesa3
					+ ", mesa4= " + mesa4);
			System.out.println("****");
			System.out.println();
		}
		
		
		// Para mostrar por pantalla tenemos que mostrarlo antes de que se 
		public void run() {
			int maquinaSolicitada = -1, mesaSolicitada = -1;
			try {
				maquinaSolicitada = maquina.solicitarMaquina();
				sleep(this.x_maquina);
				maquina.liberarMaquina(maquinaSolicitada);

				mesaSolicitada = mesa.solicitarMesa(this.y_mesa);
				// mostrarPorPantalla está siendo protegida mediante la palabra clave 
				// synchronized
				 mostrarPorPantalla(maquinaSolicitada, mesaSolicitada);
				sleep(this.y_mesa);

			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}

			mesa.liberarMesa(mesaSolicitada, mesaSolicitada);

		}

		public void setCliente(MonitorMaquinas maq, MonitorMesa mes, int i) {
			this.maquina = maq;
			this.mesa = mes;
			this.id = i;
		}

	}
}
