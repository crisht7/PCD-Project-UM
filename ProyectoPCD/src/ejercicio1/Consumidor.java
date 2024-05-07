package ejercicio1;

/**
 * La clase Consumidor implementa la interfaz Runnable y representa un hilo consumidor.
 * Cada hilo consumidor realiza operaciones en un bloque específico del array compartido.
 */
public class Consumidor implements Runnable {
    private final int id;
    
    /**
     * Crea una instancia de Consumidor con el ID especificado.
     * 
     * @param id el ID del consumidor
     */
    public Consumidor(int id) {
        this.id = id;
    }
    
    /**
     * El método run se ejecuta cuando se inicia el hilo consumidor.
     * Realiza operaciones en un bloque específico del array compartido y guarda el resultado en el array solución.
     */
    @Override
    public void run() {
        int inicioIndice = id * Main.TAM_BLOQUE; // Definimos a qué bloque pertenece el hilo
        int finIndice = Math.min(inicioIndice + Main.TAM_BLOQUE, Main.TAM_ARRAY); // Ajustamos finIndice si excede el tamaño del array
        int sum = 0;
        
        for (int i = inicioIndice; i < finIndice - 1; i += 2) { // Asegurar que i no exceda finIndice - 1
            // Operamos respectivamente entre los números con sus operaciones generadas en Productor
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
        
        // Nos aseguramos la exclusión a la hora de escribir en el array solución 
        Main.cerrojo.lock();
        try {
            Main.solucion[id] = sum;
            System.out.println("Hilo consumidor " + id + " : " + sum);
        } finally {
            Main.cerrojo.unlock();
        }
    }
}

