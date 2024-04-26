package ejercicio1;

public class Sumador implements Runnable{
	private int[] solucion;
	public Sumador(int[] solucion) {
		this.solucion = solucion;
	}
	
	@Override
	public void run() {

        int sum = 0;
        for (int resultado : solucion) {
            sum += resultado;
        }
        System.out.println("Suma total: " + sum);
	}
}
