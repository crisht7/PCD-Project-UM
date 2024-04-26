package ejercicio1;

public class Consumidor implements Runnable {
    private final int id;
    
    public Consumidor(int id) {
        this.id = id;
    }
    
    @Override
    public void run() {
        int inicioIndice = id * Main.TAM_BLOQUE; //Definimos a que bloque pertenece el hilo 
        int finIndice = Math.min(inicioIndice + Main.TAM_BLOQUE, Main.TAM_ARRAY); // Ajustamos finIndice si excede el tamaño del array
        int sum = 0;
        
        for (int i = inicioIndice; i < finIndice - 1; i += 2) { // Asegurar que i no exceda finIndice - 1
            //Operamos respectivamente entre los numeros con sus operaciones generadas en Productor
        	int num = Main.array[i];
            int operacion = Main.array[i + 1];
            
            switch (operacion) {
                case 1: // Suma
                    sum += num;
                    break;
                case 2: // Resta
                    sum -= num;
                    break;
                case 3: // Multiplicación
                    sum *= num;
                    break;
            }
        }
        //Nos aseguramos la exclusión a la hora de escribir en el array solución 
        Main.cerrojo.lock();
        try {
            Main.solucion[id] = sum;
            System.out.println("Hilo consumidor " + id + " : " + sum);
        } finally {
            Main.cerrojo.unlock();
        }
    }
}

