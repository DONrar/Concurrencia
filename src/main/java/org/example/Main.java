package org.example;

/**
 * Taller: Concurrencia y Paralelismo en Java
 * Ejercicio 2 – Concurrencia
 *
 * Simula una cuenta bancaria compartida donde 3 clientes (hilos)
 * intentan retirar $400 al mismo tiempo.
 * Se usa sincronización para evitar saldo negativo y race conditions.
 */
public class Main {
    /**
     * Cuenta bancaria compartida.
     * El método retirar es 'synchronized' para garantizar que solo
     * un hilo a la vez pueda modificar el saldo (exclusión mutua).
     */
    static class CuentaBancaria {
        private int saldo;

        public CuentaBancaria(int saldoInicial) {
            this.saldo = saldoInicial;
        }

        /**
         * Intenta retirar un monto de la cuenta.
         * 'synchronized' evita condiciones de carrera (Race Condition):
         * cuando un hilo entra al método adquiere el monitor del objeto,
         * bloqueando a los demás hasta que termine.
         */
        public synchronized void retirar(String cliente, int monto) {
            System.out.printf("[%s] intenta retirar $%d | saldo actual: $%d%n",
                    cliente, monto, saldo);

            if (saldo >= monto) {
                // Simular pequeña demora (como una operación real en BD)
                try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                saldo -= monto;
                System.out.printf("[%s] retiro EXITOSO  | saldo restante: $%d%n",
                        cliente, saldo);
            } else {
                System.out.printf("[%s] retiro RECHAZADO| fondos insuficientes (saldo: $%d)%n",
                        cliente, saldo);
            }
        }

        public int getSaldo() { return saldo; }
    }

    /**
     * Hilo que representa a un cliente intentando retirar dinero.
     */
    static class Cliente implements Runnable {
        private final CuentaBancaria cuenta;
        private final String nombre;
        private final int montoRetiro;

        public Cliente(CuentaBancaria cuenta, String nombre, int montoRetiro) {
            this.cuenta = cuenta;
            this.nombre = nombre;
            this.montoRetiro = montoRetiro;
        }

        @Override
        public void run() {
            cuenta.retirar(nombre, montoRetiro);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        CuentaBancaria cuenta = new CuentaBancaria(1000);

        System.out.println("=".repeat(55));
        System.out.println("Cuenta bancaria compartida — saldo inicial: $1000");
        System.out.println("Cada cliente intenta retirar $400");
        System.out.println("=".repeat(55));

        // Crear 3 hilos-clientes
        Thread c1 = new Thread(new Cliente(cuenta, "Cliente-1", 400));
        Thread c2 = new Thread(new Cliente(cuenta, "Cliente-2", 400));
        Thread c3 = new Thread(new Cliente(cuenta, "Cliente-3", 400));

        // Iniciar todos al mismo tiempo para generar concurrencia real
        c1.start();
        c2.start();
        c3.start();

        // Esperar a que todos terminen
        c1.join();
        c2.join();
        c3.join();

        System.out.println("=".repeat(55));
        System.out.printf("Saldo final de la cuenta: $%d%n", cuenta.getSaldo());
        System.out.println("(El saldo NUNCA quedó negativo gracias a 'synchronized')");
    }
}