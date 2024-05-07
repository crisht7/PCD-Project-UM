package ejercicio3;

import ejercicio3.MonitorCliente.Cliente;;

/**
 * La clase MainMonitor es la clase principal del programa. 
 * Se encarga de crear instancias de los monitores y los clientes, 
 * y de iniciar la ejecución de los clientes.
 */
public class MainMonitor {
	public static void main(String[] args) {
		MonitorMaquina maquinas = new MonitorMaquina();
		MonitorMesa mesas = new MonitorMesa();
		MonitorCliente monitorCliente = new MonitorCliente();
		Cliente[] clientes = new Cliente[50];
		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = monitorCliente.new Cliente(maquinas, mesas, i);
			clientes[i].start();
		}
		
	}
}
