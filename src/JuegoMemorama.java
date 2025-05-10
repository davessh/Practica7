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
        for (int i = 1; i <= 8; i++) {
            Tarjeta tarjeta1 = new TarjetaNormal("ruta/img" + i + ".png", "img" + i);
            Tarjeta tarjeta2 = new TarjetaNormal("ruta/img" + i + ".png", "img" + i);
            tablero.add(tarjeta1);
            tablero.add(tarjeta2);
        }
        // futura implemntacion de imagenes con identificador
        tablero.add(new TarjetaComodin("ruta/comodin1.png", "comodin1"));
        tablero.add(new TarjetaComodin("ruta/comodin2.png", "comodin2"));
        tablero.add(new TarjetaTrampa("ruta/trampa1.png", "trampa1"));
        tablero.add(new TarjetaTrampa("ruta/trampa2.png", "trampa2"));
    }

    public void jugar() {
        while (!juegoTerminado()) {
            mostrarTablero();
            System.out.println("\nTurno del jugador " + (jugadorActual + 1));

            int primerPosicion = seleccionarPosicion("Selecciona primera tarjeta");
            Tarjeta primeraTarjeta = tablero.get(primerPosicion);
            primeraTarjeta.voltearTarjeta();
            mostrarTablero();

            // Caso de tarjeta trampa en la primera selección
            if (primeraTarjeta instanceof TarjetaTrampa) {
                primeraTarjeta.activarEfectoEspecial();
                primeraTarjeta.marcarPareja(); // para que quede descubierta y no afecte el final
                jugadorActual = (jugadorActual + 1) % numJugadores;
                continue;
            }

            // Caso especial: Comodín como primera carta
            if (primeraTarjeta instanceof TarjetaComodin) {
                manejarTarjetaComodin(primeraTarjeta);
                continue;
            }

            int segundaPosicion;
            do {
                segundaPosicion = seleccionarPosicion("Selecciona segunda tarjeta");
            } while (segundaPosicion == primerPosicion);

            Tarjeta segundaTarjeta = tablero.get(segundaPosicion);
            segundaTarjeta.voltearTarjeta();
            mostrarTablero();

            // Caso de tarjeta trampa en la segunda seleccion
            if (segundaTarjeta instanceof TarjetaTrampa) {
                segundaTarjeta.activarEfectoEspecial();
                segundaTarjeta.marcarPareja();
                primeraTarjeta.voltearTarjeta(); // volver a cubrir la primera
                jugadorActual = (jugadorActual + 1) % numJugadores;
                continue;
            }

            //  Comodín como segunda carta
            if (segundaTarjeta instanceof TarjetaComodin) {
                primeraTarjeta.voltearTarjeta(); // Volvemos a ocultar la primera carta
                manejarTarjetaComodin(segundaTarjeta);
                continue;
            }

            if (primeraTarjeta.esPareja(segundaTarjeta)) {
                System.out.println("Pareja encontrada");
                primeraTarjeta.marcarPareja();
                segundaTarjeta.marcarPareja();
                puntajes[jugadorActual]++;
            } else {
                System.out.println("No es pareja.");
                primeraTarjeta.voltearTarjeta();
                segundaTarjeta.voltearTarjeta();
                jugadorActual = (jugadorActual + 1) % numJugadores;
            }
        }

        mostrarGanador();
    }

    private void manejarTarjetaComodin(Tarjeta comodin) {
        comodin.activarEfectoEspecial();
        comodin.marcarPareja(); // Marcamos el comodin como descubierto

        // Permitir al jugador elegir cualquier carta
        int posicionElegida = seleccionarPosicion("Selecciona una tarjeta para revelar su pareja");
        Tarjeta tarjetaElegida = tablero.get(posicionElegida);

        if (tarjetaElegida instanceof TarjetaTrampa) {
            System.out.println("Has elegido una trampa, pero el comodín te protege.");
            tarjetaElegida.marcarPareja(); // La marcamos como descubierta
            puntajes[jugadorActual]++;
            return;
        }

        if (tarjetaElegida instanceof TarjetaComodin) {
            System.out.println("Has elegido otro comodín, se marca como encontrado.");
            tarjetaElegida.marcarPareja();
            puntajes[jugadorActual]++; // Se da un punto por usar el comodín
            return;
        }

        // Buscar la pareja de la tarjeta elegida
        tarjetaElegida.voltearTarjeta();
        mostrarTablero();

        Tarjeta pareja = encontrarPareja(tarjetaElegida);
        if (pareja != null) {
            System.out.println("El comodín ha revelado la pareja");
            pareja.voltearTarjeta();
            mostrarTablero();

            // Marcamos ambas como encontradas
            tarjetaElegida.marcarPareja();
            pareja.marcarPareja();
            puntajes[jugadorActual]++; // Sumamos un punto
        } else {
            System.out.println("Esta tarjeta no tiene pareja disponible.");
            tarjetaElegida.voltearTarjeta(); // La volvemos a ocultar
        }
    }

    private Tarjeta encontrarPareja(Tarjeta tarjeta) {
        for (Tarjeta t : tablero) {
            if (!t.estaDescubierta() && !t.tienePareja() && t != tarjeta && tarjeta.esPareja(t)) {
                return t;
            }
        }
        return null;
    }

    private int seleccionarPosicion(String mensaje) {
        int posicion;
        while (true) {
            System.out.print(mensaje + " (0-" + (tablero.size() - 1) + "): ");
            posicion = scanner.nextInt();
            if (posicion >= 0 && posicion < tablero.size() && !tablero.get(posicion).estaDescubierta()) break;
            System.out.println("Invalido, intenta de nuevo.");
        }
        return posicion;
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