import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Collections;
import java.util.ArrayList;

public class TableroMemorama extends JFrame {
    private JButton[] botones;
    private TarjetaNormal[] tarjetas;
    private ArrayList<String> imagenes;
    private int primerBotonSeleccionado = -1;
    private boolean esperando = false;
    private final String RUTA_BASE = "C:\\Users\\Usuario\\IdeaProjects\\Practica7\\imagenes\\";
    private final String RUTA_PORTADA = RUTA_BASE + "portada.png";
    private String modoJuego;
    private JLabel[] etiquetasJugadores;
    private JPanel panelJugadores;
    private JuegoMemorama memorama;
    private int jugadorActual = 0;
    private int[] puntuaciones;
    private String[] nombresJugadores;
    private JLabel etiquetaTurno;
    private int paresEncontradosEnTurno = 0;

    public TableroMemorama(TarjetaNormal[] tarjetasParam, String modoJuego, String[] nombresJugadores, int[] puntuacionesIniciales) {
        setTitle("Juego Memorama");
        //setLayout(new GridLayout(3, 6));  // 4x4 para las 16 tarjetas
        setLayout(new BorderLayout());
        JPanel panelBotones = new JPanel(new GridLayout(3,6));
        panelBotones.setPreferredSize(new Dimension(1200, 600));
        setSize(1000, 500);  // Tamaño más grande para ver mejor las imágenes
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // Asignar las tarjetas recibidas al campo de la clase
        this.tarjetas = tarjetasParam;
        this.modoJuego = modoJuego;
        configurarPanelJugadores(nombresJugadores, puntuacionesIniciales);
        configurarPanelTurnos();

        JPanel panelModoJuego = new JPanel();
        JLabel modoJuegoLabel = new JLabel("Modo de juego: " + modoJuego.toUpperCase());
        modoJuegoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panelModoJuego.add(modoJuegoLabel);
        add(panelModoJuego, BorderLayout.SOUTH);

        imagenes = new ArrayList<>();
        for (int i = 0; i < tarjetas.length; i++) {
            String rutaCompleta = RUTA_BASE + modoJuego + "\\" + tarjetas[i].getRutaImagen();
            imagenes.add(rutaCompleta);
        }
        Dimension tamañoBotones = new Dimension(200,200);
        // Crear botones para el tablero
        botones = new JButton[18];
        for (int i = 0; i < botones.length; i++) {
            botones[i] = new JButton();

            botones[i].setPreferredSize(tamañoBotones);
            botones[i].setMinimumSize(tamañoBotones);
            botones[i].setMaximumSize(tamañoBotones);
            ImageIcon iconoPortada = new ImageIcon(RUTA_PORTADA);

            // Redimensionar la imagen
            Image img = iconoPortada.getImage();
            Image imgRedimensionada = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            iconoPortada = new ImageIcon(imgRedimensionada);

            botones[i].setIcon(iconoPortada);
            botones[i].setBackground(Color.LIGHT_GRAY);
            botones[i].setFocusable(false);

            final int indice = i;
            botones[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    botonClickeado(indice);
                }
            });
            panelBotones.add(botones[i]);
        }
        add(panelBotones, BorderLayout.CENTER);
        setMinimumSize(new Dimension(800, 450));

        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void configurarPanelJugadores(String[] nombresJugadores, int[] puntuaciones) {
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
        if (nombresJugadores == null || nombresJugadores.length == 0) {
            nombresJugadores = new String[]{"Jugador 1"};
            puntuaciones = new int[]{0};
        }
        JPanel panelTurno = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        panelTurno.setBackground(new Color(240, 240, 240));

        etiquetaTurno = new JLabel("Turno de: " + nombresJugadores[jugadorActual]);
        etiquetaTurno.setFont(new Font("Arial", Font.BOLD, 16));
        etiquetaTurno.setForeground(Color.RED);

        panelTurno.add(etiquetaTurno);
        add(panelTurno, BorderLayout.SOUTH);
    }

    private void actualizarPuntuacion() {
        for (int i = 0; i < nombresJugadores.length; i++) {
            etiquetasJugadores[i].setText(
                    nombresJugadores[i] + ": " + puntuaciones[i] + " puntos");
        }
    }

    private void cambiarTurno() {
        jugadorActual = (jugadorActual + 1) % nombresJugadores.length;
        etiquetaTurno.setText("Turno de: " + nombresJugadores[jugadorActual]);
        paresEncontradosEnTurno = 0;
    }

    private void botonClickeado(int indice) {
        // Verificar si el botón ya está descubierto o si estamos esperando
        if (esperando || botones[indice].isEnabled() == false) {
            return;
        }

        if (indice == primerBotonSeleccionado) {
            return;
        }

        // Cargar la imagen y redimensionarla
        String rutaImagen = imagenes.get(indice);
        ImageIcon icono = new ImageIcon(rutaImagen);

        // Redimensionar la imagen para que se vea mejor
        Image img = icono.getImage();
        if (img != null) {
            Image imgRedimensionada = img.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            icono = new ImageIcon(imgRedimensionada);
        } else {
            System.out.println("Error: No se pudo cargar la imagen: " + rutaImagen);
            botones[indice].setBackground(Color.RED);
            botones[indice].setText("Error");
            return;
        }

        botones[indice].setIcon(icono);

        // Si es la primera selección
        if (primerBotonSeleccionado == -1) {
            primerBotonSeleccionado = indice;
        } else {
            // Si es la segunda selección
            esperando = true;
            final int primerIndice = primerBotonSeleccionado;

            // Verificamos que los índices sean válidos antes de acceder al array
            if (primerIndice >= 0 && primerIndice < tarjetas.length &&
                    indice >= 0 && indice < tarjetas.length) {

                // Verificamos si las tarjetas forman una pareja
                if (tarjetas[primerIndice] != null && tarjetas[indice] != null &&
                        tarjetas[primerIndice].getIdentificador().equals(tarjetas[indice].getIdentificador())) {
                    puntuaciones[jugadorActual]++;
                    paresEncontradosEnTurno++;
                    actualizarPuntuacion();
                    //JOptionPane.showMessageDialog(this, "Pareja encontrada");
                    botones[primerIndice].setEnabled(false);
                    botones[indice].setEnabled(false);
                    esperando = false;
                } else {
                    Timer timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Volver a mostrar la imagen de portada
                            ImageIcon iconoPortada = new ImageIcon(RUTA_PORTADA);
                            Image img = iconoPortada.getImage();
                            Image imgRedimensionada = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                            iconoPortada = new ImageIcon(imgRedimensionada);

                            botones[primerIndice].setIcon(iconoPortada);
                            botones[indice].setIcon(iconoPortada);
                            esperando = false;
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();

                    if (paresEncontradosEnTurno == 0) {
                        cambiarTurno();
                    } else {
                        paresEncontradosEnTurno = 0;
                    }
                }
            }
            primerBotonSeleccionado = -1;
        }
    }
    }

