import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TableroMemorama extends JFrame {
    private JButton[][] botones;
    private JuegoMemorama juego;
    private int primerFilaSeleccionada = -1;
    private int primerColumnaSeleccionada = -1;
    private boolean esperando = false;
    private final String RUTA_BASE = "C:\\Users\\Usuario\\IdeaProjects\\Practica7.2\\imagenes\\";
    private final String RUTA_PORTADA = RUTA_BASE + "portada.png";
    private JLabel[] etiquetasJugadores;
    private JPanel panelJugadores;
    private int jugadorActual = 0;
    private int[] puntuaciones;
    private String[] nombresJugadores;
    private JLabel etiquetaTurno;
    private int paresEncontradosEnTurno = 0;
    private Timer timer;

    public TableroMemorama(JuegoMemorama juego, String[] nombresJugadores, int[] puntuacionesIniciales) {
        this.juego = juego;
        this.nombresJugadores = nombresJugadores;
        this.puntuaciones = puntuacionesIniciales;

        setTitle("Juego Memorama - " + juego.getModoJuego().toUpperCase());
        setLayout(new BorderLayout());

        // Configurar paneles de jugadores y turno
        configurarPanelJugadores();
        configurarPanelTurnos();

        // Panel principal para los botones
        JPanel panelBotones = new JPanel(new GridLayout(juego.getFilas(), juego.getColumnas()));
        panelBotones.setPreferredSize(new Dimension(1200, 600));

        // Crear botones para el tablero
        botones = new JButton[juego.getFilas()][juego.getColumnas()];
        Dimension tamañoBotones = new Dimension(200, 200);

        for (int i = 0; i < juego.getFilas(); i++) {
            for (int j = 0; j < juego.getColumnas(); j++) {
                botones[i][j] = new JButton();
                botones[i][j].setPreferredSize(tamañoBotones);

                // Configurar imagen de portada
                ImageIcon iconoPortada = new ImageIcon(RUTA_PORTADA);
                Image img = iconoPortada.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                botones[i][j].setIcon(new ImageIcon(img));
                botones[i][j].setBackground(Color.LIGHT_GRAY);
                botones[i][j].setFocusable(false);

                final int fila = i;
                final int columna = j;
                botones[i][j].addActionListener(e -> botonClickeado(fila, columna));

                panelBotones.add(botones[i][j]);
            }
        }

        add(panelBotones, BorderLayout.CENTER);

        // Panel de preferencias en la parte inferior
        JPanel panelPreferencias = new JPanel(new BorderLayout());
        panelPreferencias.setBorder(BorderFactory.createTitledBorder("Juego en curso"));

        // Panel para el modo de juego
        JPanel panelModoJuego = new JPanel();
        JLabel modoJuegoLabel = new JLabel("Modo de juego: " + juego.getModoJuego().toUpperCase());
        modoJuegoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panelModoJuego.add(modoJuegoLabel);

        // Panel para el turno actual
        JPanel panelTurnoActual = new JPanel();
        etiquetaTurno = new JLabel("Turno actual: " + nombresJugadores[jugadorActual]);
        etiquetaTurno.setFont(new Font("Arial", Font.BOLD, 14));
        etiquetaTurno.setForeground(Color.RED);
        panelTurnoActual.add(etiquetaTurno);

        panelPreferencias.add(panelModoJuego, BorderLayout.WEST);
        panelPreferencias.add(panelTurnoActual, BorderLayout.EAST);

        add(panelPreferencias, BorderLayout.SOUTH);

        setMinimumSize(new Dimension(800, 450));
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        configurarTarjetasEspeciales();
        if (juego.getModoJuego().equalsIgnoreCase("animales")) {
            String parejaEspecial = TarjetaAnimal.getParejaEspecial();
            if (parejaEspecial != null) {
                JOptionPane.showMessageDialog(this,
                        "¡Bienvenido al modo Animales!\nLa pareja especial de esta partida es: " +
                                parejaEspecial.toUpperCase() + "\nEncontrarla te dará 2 puntos en lugar de 1.",
                        "Pareja Especial",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void configurarPanelJugadores() {
        panelJugadores = new JPanel();
        panelJugadores.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelJugadores.setBackground(new Color(240, 240, 240));
        panelJugadores.setBorder(BorderFactory.createTitledBorder("Jugadores"));

        etiquetasJugadores = new JLabel[nombresJugadores.length];

        for (int i = 0; i < nombresJugadores.length; i++) {
            etiquetasJugadores[i] = new JLabel(
                    nombresJugadores[i] + ": " + puntuaciones[i] + " puntos");
            etiquetasJugadores[i].setFont(new Font("Arial", Font.BOLD, 16));
            etiquetasJugadores[i].setForeground(Color.BLUE);
            panelJugadores.add(etiquetasJugadores[i]);
        }

        add(panelJugadores, BorderLayout.NORTH);
    }

    private void configurarPanelTurnos() {
    }

    private void actualizarPuntuacion() {
        for (int i = 0; i < nombresJugadores.length; i++) {
            etiquetasJugadores[i].setText(
                    nombresJugadores[i] + ": " + puntuaciones[i] + " puntos");
        }
    }

    private void cambiarTurno() {
        jugadorActual = (jugadorActual + 1) % nombresJugadores.length;
        etiquetaTurno.setText("Turno actual: " + nombresJugadores[jugadorActual]);
        paresEncontradosEnTurno = 0;
    }

    private void botonClickeado(int fila, int columna) {
        if (!juego.isTableroHabilitado() || esperando) {
            return;
        }

        // Seleccionar la tarjeta en el juego
        int estadoSeleccion = juego.seleccionarTarjeta(fila, columna);

        if (estadoSeleccion == 0) {
            return; // Selección inválida
        }

        // Mostrar la imagen de la tarjeta
        mostrarImagenTarjeta(fila, columna);

        if (estadoSeleccion == 1) {
            // Primera tarjeta
            primerFilaSeleccionada = fila;
            primerColumnaSeleccionada = columna;
        } else if (estadoSeleccion == 2) {
            // Segunda
            esperando = true;

            timer = new Timer(1000, e -> procesarSeleccion(fila, columna));
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void mostrarImagenTarjeta(int fila, int columna) {
        Tarjeta tarjeta = juego.getTarjeta(fila, columna);
        if (tarjeta == null) return;

        String rutaImagen = RUTA_BASE + juego.getModoJuego() + "\\" + tarjeta.getRutaImagen();
        ImageIcon icono = new ImageIcon(rutaImagen);

        Image img = icono.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        botones[fila][columna].setIcon(new ImageIcon(img));
    }

    private void procesarSeleccion(int segundaFila, int segundaColumna) {
        boolean sonPareja = juego.procesarSeleccion();
        String primeraCarta = getPrimeraCartaSeleccionada();
        String segundaCarta = getSegundaCartaSeleccionada(segundaFila, segundaColumna);

        System.out.println("Cartas seleccionadas: " + primeraCarta + " y " + segundaCarta);

        if (sonPareja) {
            Tarjeta tarjeta1 = juego.getTarjeta(primerFilaSeleccionada, primerColumnaSeleccionada);
            Tarjeta tarjeta2 = juego.getTarjeta(segundaFila, segundaColumna);

            // Activar efectos especiales primero (mostrar GIF)
            if (tarjeta1.tieneEfectoEspecial()) tarjeta1.activarEfectoEspecial();
            if (tarjeta2.tieneEfectoEspecial()) tarjeta2.activarEfectoEspecial();

            // Configurar un temporizador para deshabilitar los botones después de mostrar el GIF
            Timer timerDeshabilitar = new Timer(1000, e -> {
                // Sumar punto (el efecto especial puede sumar puntos extra)
                puntuaciones[jugadorActual]++;
                paresEncontradosEnTurno++;
                actualizarPuntuacion();

                // Deshabilitar los botones de la pareja encontrada
                botones[primerFilaSeleccionada][primerColumnaSeleccionada].setEnabled(false);
                botones[segundaFila][segundaColumna].setEnabled(false);

                // Verificar si el juego ha terminado
                if (juego.juegoCompletado()) {
                    JOptionPane.showMessageDialog(this, "Juego completado\n" + obtenerGanador());
                }

                primerFilaSeleccionada = -1;
                primerColumnaSeleccionada = -1;
                esperando = false;
            });
            timerDeshabilitar.setRepeats(false);
            timerDeshabilitar.start();
        } else {
            // Para no parejas, mantener el comportamiento original
            voltearTarjetasNoEmparejadas();

            if (paresEncontradosEnTurno == 0) {
                cambiarTurno();
            } else {
                paresEncontradosEnTurno = 0;
                cambiarTurno();
            }

            primerFilaSeleccionada = -1;
            primerColumnaSeleccionada = -1;
            esperando = false;
        }
    }

    private void voltearTarjetasNoEmparejadas() {
        juego.voltearTarjetasNoEmparejadas();

        ImageIcon iconoPortada = new ImageIcon(RUTA_PORTADA);
        Image imgPortada = iconoPortada.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        iconoPortada = new ImageIcon(imgPortada);

        for (int i = 0; i < juego.getFilas(); i++) {
            for (int j = 0; j < juego.getColumnas(); j++) {
                Tarjeta tarjeta = juego.getTarjeta(i, j);
                if (tarjeta != null && !tarjeta.tienePareja() && !tarjeta.estaDescubierta()) {
                    botones[i][j].setIcon(iconoPortada);
                }
            }
        }
    }

    private String obtenerGanador() {
        int maxPuntos = -1;
        ArrayList<Integer> ganadores = new ArrayList<>();

        for (int i = 0; i < puntuaciones.length; i++) {
            if (puntuaciones[i] > maxPuntos) {
                maxPuntos = puntuaciones[i];
                ganadores.clear();
                ganadores.add(i);
            } else if (puntuaciones[i] == maxPuntos) {
                ganadores.add(i);
            }
        }

        if (ganadores.size() > 1) {
            StringBuilder empate = new StringBuilder("¡Empate entre: ");
            for (int i = 0; i < ganadores.size(); i++) {
                if (i > 0) empate.append(", ");
                empate.append(nombresJugadores[ganadores.get(i)]);
            }
            return empate.toString();
        } else {
            return "¡Ganador: " + nombresJugadores[ganadores.get(0)];
        }
    }
    public void sumarPuntoJugadorActual() {
        puntuaciones[jugadorActual]++;
        actualizarPuntuacion();
        paresEncontradosEnTurno++;

        // Verificar si con este punto extra se completa el juego
        if (juego.juegoCompletado()) {
            JOptionPane.showMessageDialog(this, "Juego completado\n" + obtenerGanador());
        }
    }

    /**
     * Fuerza un cambio de turno (para el efecto de la carta trampa)
     */
    public void forzarCambioTurno() {
        cambiarTurno();
    }

    /**
     * Método que debe llamarse en la inicialización del juego para configurar
     * la referencia del tablero en las cartas especiales
     */
    public void configurarTarjetasEspeciales() {
        for (int i = 0; i < juego.getFilas(); i++) {
            for (int j = 0; j < juego.getColumnas(); j++) {
                Tarjeta tarjeta = juego.getTarjeta(i, j);
                if (tarjeta instanceof TarjetaAnimal) {
                    ((TarjetaAnimal) tarjeta).setTablero(this);
                } else if (tarjeta instanceof TarjetaInstrumento) {
                    ((TarjetaInstrumento) tarjeta).setTablero(this);
                }
            }
        }
    }

    public String getPrimeraCartaSeleccionada() {
        if (primerFilaSeleccionada == -1 || primerColumnaSeleccionada == -1) {
            return null;
        }
        Tarjeta tarjeta = juego.getTarjeta(primerFilaSeleccionada, primerColumnaSeleccionada);
        return tarjeta != null ? tarjeta.getIdentificador() : null;
    }


    public String getSegundaCartaSeleccionada(int fila, int columna) {
        Tarjeta tarjeta = juego.getTarjeta(fila, columna);
        return tarjeta != null ? tarjeta.getIdentificador() : null;
    }

    public int getJugadorActual() {
        return jugadorActual;
    }
    public JButton[][] getBotones() {
        return botones;
    }
    public String getRUTA_BASE() {
        return RUTA_BASE;
    }
    public JuegoMemorama getJuego() {
        return juego;
    }
}
