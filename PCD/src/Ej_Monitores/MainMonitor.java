package Ej_Monitores;

import Ej_Monitores.MonitorCliente.Cliente;

public class MainMonitor {
	public static void main(String[] args) {
		MonitorMaquinas maquinas = new MonitorMaquinas();
		MonitorMesa mesas = new MonitorMesa();
		MonitorCliente monitorCliente = new MonitorCliente();
		Cliente[] clientes = new Cliente[50];
		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = monitorCliente.new Cliente(maquinas, mesas, i);
			clientes[i].start();
		}
		
	}
}
