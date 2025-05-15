import javax.swing.*;
import java.awt.*;

public class Menu {
    private JFrame frame;
    private JuegoMemorama juego;
    private JButton botonJugar, botonSalir;
    private int cantidadDeJugadores;
    private String modoJuego;
    private TableroMemorama tablero;
    TarjetaNormal[] tarjetas = JuegoMemorama.crearTarjetasAnimales();
    String[] nombresJugadores;
    int[] puntuacionesIniciales = {0, 0,0};
    public Menu() {
        this.cantidadDeJugadores = 0;
        empezarJuego();
    }
    public void empezarJuego() {
        frame = new JFrame("Memorama");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.getRootPane().setDefaultButton(botonJugar);

        JPanel backgroundPanel = new JPanel() {
            private Image fondo;

            {
                try {
                    //fondo = new ImageIcon("C:\\Users\\GF76\\IdeaProjects\\Practica-7\\src\\MEMORAMA.png").getImage();
                    fondo = new ImageIcon("C:\\Users\\Usuario\\IdeaProjects\\Practica7\\src\\MEMORAMA.png").getImage();
                    fondo = fondo.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondo != null) {
                    g.drawImage(fondo, 0, 0, this);
                }
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setOpaque(false);

        panelMenu.add(Box.createVerticalGlue());
        botonJugar = new JButton("Jugar");
        botonJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonJugar.setMaximumSize(new Dimension(270, 70));
        botonJugar.setFont(new Font("Century Gothic",Font.BOLD, 30));
        botonJugar.setFocusPainted(false);
        botonJugar.addActionListener(e -> ingresarCantidadDeJugadores());
        panelMenu.add(botonJugar);

        panelMenu.add(Box.createVerticalStrut(10));

        botonSalir = new JButton("Salir");
        botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSalir.setMaximumSize(new Dimension(250, 55));
        botonSalir.setFont(new Font("Century Gothic", Font.BOLD, 25));
        botonSalir.setFocusPainted(false);
        panelMenu.add(botonSalir);

        panelMenu.add(Box.createVerticalGlue());

        botonSalir.addActionListener(e -> System.exit(0));
        botonJugar.addActionListener(e -> {
            frame.dispose();
        });

        backgroundPanel.add(panelMenu, BorderLayout.CENTER);
        frame.add(backgroundPanel);
        frame.setVisible(true);
    }
    public void ingresarCantidadDeJugadores() {
        cantidadDeJugadores = 0;
        while (cantidadDeJugadores < 2 || cantidadDeJugadores > 4) {
            String input = JOptionPane.showInputDialog(frame,
                    "Ingrese el número de jugadores (2 a 4):",
                    "Número de Jugadores", JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                return;
            }

            try {
                cantidadDeJugadores = Integer.parseInt(input);
                if (cantidadDeJugadores < 2 || cantidadDeJugadores > 4) {
                    JOptionPane.showMessageDialog(frame,
                            "Por favor ingrese un número entre 2 y 4",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame,
                        "Por favor ingrese un número válido",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        ingresarNombreJugadores();
    }

    public void ingresarNombreJugadores(){
        nombresJugadores = new String[cantidadDeJugadores];

        for (int i = 0; i < cantidadDeJugadores; i++) {
            String nombre = JOptionPane.showInputDialog(frame,
                    "Ingrese el nombre del Jugador " + (i + 1) + ":",
                    "Nombre del Jugador", JOptionPane.QUESTION_MESSAGE);

            if (nombre == null || nombre.trim().isEmpty()) {
                nombre = "Jugador " + (i + 1);
            }

            nombresJugadores[i] = nombre;
        }

        seleccionarModoDeJuego();
    }

    private JButton crearBotonModo(String nombreModo, String rutaImagen) {
        JButton boton = new JButton();
        boton.setLayout(new BorderLayout());

        ImageIcon icono = new ImageIcon(rutaImagen);
        try {
            Image img = icono.getImage();
            if (img != null && img.getWidth(null) > 0) {
                img = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                icono = new ImageIcon(img);
            } else {
                icono = null;
            }
        } catch (Exception e) {
            icono = null;
        }

        if (icono != null) {
            JLabel labelImagen = new JLabel(icono);
            labelImagen.setHorizontalAlignment(JLabel.CENTER);
            boton.add(labelImagen, BorderLayout.CENTER);
        } else {
            JPanel panelColor = new JPanel();
            panelColor.setPreferredSize(new Dimension(150, 150));
            panelColor.setBackground(nombreModo.equals("ANIMALES") ? new Color(152, 251, 152) :
                    nombreModo.equals("DEPORTISTAS") ? new Color(135, 206, 250) :
                            new Color(255, 182, 193));
            boton.add(panelColor, BorderLayout.CENTER);
        }

        JLabel labelTexto = new JLabel(nombreModo);
        labelTexto.setFont(new Font("Century Gothic", Font.BOLD, 16));
        labelTexto.setHorizontalAlignment(JLabel.CENTER);
        labelTexto.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        boton.add(labelTexto, BorderLayout.SOUTH);

        return boton;
    }
    public void seleccionarModoDeJuego() {
        JFrame frameSeleccion = new JFrame("Modo de Juego");
        frameSeleccion.setSize(600, 400);
        frameSeleccion.setLocationRelativeTo(frame);
        frameSeleccion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panelModos = new JPanel(new GridLayout(1, 3, 20, 0));
        panelModos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton botonAnimales = crearBotonModo("ANIMALES", "C:\\Users\\Usuario\\IdeaProjects\\Practica7\\src\\animalesicono.png");
        botonAnimales.addActionListener(e -> {
            modoJuego = "animales";
            frameSeleccion.dispose();
            new TableroMemorama(tarjetas,modoJuego,nombresJugadores,puntuacionesIniciales);
        });

        JButton botonDeportistas = crearBotonModo("DEPORTISTAS", "C:\\Users\\Usuario\\IdeaProjects\\Practica7\\src\\deportesicono.png");
        botonDeportistas.addActionListener(e -> {
            modoJuego = "DEPORTISTAS";
            frameSeleccion.dispose();

        });

        JButton botonInstrumentos = crearBotonModo("INSTRUMENTOS", "C:\\Users\\Usuario\\IdeaProjects\\Practica7\\src\\instrumentosicono.png");
        botonInstrumentos.addActionListener(e -> {
            modoJuego = "INSTRUMENTOS";
            frameSeleccion.dispose();
        });

        panelModos.add(botonAnimales);
        panelModos.add(botonDeportistas);
        panelModos.add(botonInstrumentos);

        frameSeleccion.add(panelModos);
        frameSeleccion.setVisible(true);
    }

}
