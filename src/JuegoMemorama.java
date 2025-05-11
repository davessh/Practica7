import java.util.*;

public class JuegoMemorama {
    private List<Tarjeta> tablero;
    private int numJugadores;
    private int jugadorActual;
    private int[] puntajes;
    private Scanner scanner;

    public JuegoMemorama(int numJugadores) {
        this.numJugadores = numJugadores;
        this.jugadorActual = 0;
        this.puntajes = new int[numJugadores];
        this.tablero = new ArrayList<>();
        this.scanner = new Scanner(System.in);

        inicializarTablero();
        Collections.shuffle(tablero);
    }

    private void inicializarTablero() {
        // Agregar pares de tarjetas normales
        for (int i = 1; i <= 5; i++) {
            Tarjeta t1 = new TarjetaNormal("ruta/img" + i + ".png", "img" + i);
            Tarjeta t2 = new TarjetaNormal("ruta/img" + i + ".png", "img" + i);
            tablero.add(t1);
            tablero.add(t2);
        }

        // Agregar trampas
        tablero.add(new TarjetaTrampa("ruta/trampa1.png", "trampa1"));
        tablero.add(new TarjetaTrampa("ruta/trampa2.png", "trampa2"));

        // Agregar comodín
        tablero.add(new TarjetaComodin("ruta/comodin.png", "comodin"));
    }

    public void jugar() {
        while (!juegoTerminado()) {
            mostrarTablero();
            System.out.println("\nTurno del jugador " + (jugadorActual + 1));
            System.out.print("Selecciona primera tarjeta (0-" + (tablero.size() - 1) + "): ");
            int pos1 = scanner.nextInt();
            Tarjeta t1 = tablero.get(pos1);
            t1.voltearTarjeta();

            System.out.print("Selecciona segunda tarjeta (0-" + (tablero.size() - 1) + "): ");
            int pos2 = scanner.nextInt();
            Tarjeta t2 = tablero.get(pos2);
            t2.voltearTarjeta();

            mostrarTablero();

            if (t1.tieneEfectoEspecial()) t1.activarEfectoEspecial();
            if (t2.tieneEfectoEspecial()) t2.activarEfectoEspecial();

            if (t1.esPareja(t2)) {
                System.out.println("¡Pareja encontrada!");
                t1.marcarPareja();
                t2.marcarPareja();
                puntajes[jugadorActual]++;
            } else {
                System.out.println("No es pareja.");
                t1.voltearTarjeta();
                t2.voltearTarjeta();
                jugadorActual = (jugadorActual + 1) % numJugadores;
            }
        }

        mostrarGanador();
    }

    private boolean juegoTerminado() {
        for (Tarjeta t : tablero) {
            if (!t.tienePareja() && !(t instanceof TarjetaTrampa)) return false;
        }
        return true;
    }

    private void mostrarTablero() {
        for (int i = 0; i < tablero.size(); i++) {
            Tarjeta t = tablero.get(i);
            if (t.estaDescubierta() || t.tienePareja()) {
                System.out.print("[" + t.getIdentificador() + "] ");
            } else {
                System.out.print("[" + i + "] ");
            }
        }
        System.out.println();
    }

    private void mostrarGanador() {
        System.out.println("\nPuntajes finales:");
        int max = -1;
        for (int i = 0; i < puntajes.length; i++) {
            System.out.println("Jugador " + (i + 1) + ": " + puntajes[i]);
            if (puntajes[i] > max) {
                max = puntajes[i];
            }
        }

        List<Integer> ganadores = new ArrayList<>();
        for (int i = 0; i < puntajes.length; i++) {
            if (puntajes[i] == max) ganadores.add(i + 1);
        }

        if (ganadores.size() == 1) {
            System.out.println("Ganador: Jugador " + ganadores.get(0));
        } else {
            System.out.println("Empate entre jugadores: " + ganadores);
        }
    }
}

