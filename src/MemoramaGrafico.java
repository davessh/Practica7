import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class MemoramaGrafico {

    private JFrame frame;
    private JuegoMemorama juego;
    private JButton botonJugar, botonSalir;
    private int cantidadDeJugadores;
    private String nombresJugadores[];
    private String modoJuego;
    private JButton primeraSeleccion = null;
    private JButton segundaSeleccion = null;
    private boolean bloqueado = false;

    public MemoramaGrafico() {
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
            modoJuego = "ANIMALES";
            frameSeleccion.dispose();
            jugar();
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

    private void verificarPar() {
        if (primeraSeleccion != null && segundaSeleccion != null) {
            ImageIcon img1 = (ImageIcon) primeraSeleccion.getClientProperty("imagen");
            ImageIcon img2 = (ImageIcon) segundaSeleccion.getClientProperty("imagen");

            if (img1.getDescription() == null || img2.getDescription() == null) {
                // Voltear las cartas de nuevo si no tienen descripción
                ImageIcon cubierta = new ImageIcon("ruta/a/cubierta.jpg");
                primeraSeleccion.setIcon(cubierta);
                segundaSeleccion.setIcon(cubierta);
                primeraSeleccion = null;
                segundaSeleccion = null;
                return;
            }

            String nombre1 = img1.getDescription();
            String nombre2 = img2.getDescription();
            String tipo1 = nombre1.split("_")[1].replace(".jpg", "");
            String tipo2 = nombre2.split("_")[1].replace(".jpg", "");

            if (tipo1.equals(tipo2)) {
                // Es un par - deshabilitar los botones
                primeraSeleccion.setEnabled(false);
                segundaSeleccion.setEnabled(false);
            } else {
                // No es un par - voltear las cartas de nuevo y permitir que sean seleccionadas nuevamente
                ImageIcon cubierta = new ImageIcon("ruta/a/cubierta.jpg");
                primeraSeleccion.setIcon(cubierta);
                segundaSeleccion.setIcon(cubierta);

                // Asegurarse de que los botones pueden ser clickeados de nuevo
                primeraSeleccion.setEnabled(true);
                segundaSeleccion.setEnabled(true);
            }

            // Resetear las selecciones
            primeraSeleccion = null;
            segundaSeleccion = null;
        }
    }
    public void jugar() {
        JFrame juegoFrame = new JFrame("Memorama - " + modoJuego);
        juegoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        juegoFrame.setSize(900, 700);
        juegoFrame.setLocationRelativeTo(null);

        // Panel principal
        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBackground(new Color(240, 240, 240));

        // Panel para la información de jugadores (NORTH)
        JPanel panelJugadores = new JPanel(new GridLayout(1, cantidadDeJugadores));
        panelJugadores.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelJugadores.setBackground(new Color(200, 220, 240));

        // Crear indicadores para cada jugador
        for (int i = 0; i < cantidadDeJugadores; i++) {
            JPanel panelJugador = new JPanel();
            panelJugador.setLayout(new BoxLayout(panelJugador, BoxLayout.Y_AXIS));
            panelJugador.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            panelJugador.setBackground(new Color(220, 240, 255));

            JLabel lblNombre = new JLabel(nombresJugadores[i]);
            lblNombre.setFont(new Font("Century Gothic", Font.BOLD, 16));
            lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblPuntaje = new JLabel("Puntaje: 0");
            lblPuntaje.setFont(new Font("Century Gothic", Font.PLAIN, 14));
            lblPuntaje.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblTurno = new JLabel("(Turno Actual)");
            lblTurno.setFont(new Font("Century Gothic", Font.ITALIC, 12));
            lblTurno.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblTurno.setVisible(i == 0); // Mostrar solo para el primer jugador inicialmente

            panelJugador.add(Box.createVerticalStrut(5));
            panelJugador.add(lblNombre);
            panelJugador.add(Box.createVerticalStrut(5));
            panelJugador.add(lblPuntaje);
            panelJugador.add(Box.createVerticalStrut(5));
            panelJugador.add(lblTurno);
            panelJugador.add(Box.createVerticalStrut(5));

            panelJugadores.add(panelJugador);
        }
        // Panel para la cuadrícula de tarjetas (3x6)
        JPanel panelTarjetas = new JPanel(new GridLayout(3, 6, 10, 10));
        panelTarjetas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Cargar imágenes y agrupar por etiqueta (ej: "lobo", "tigre", etc.)
        Map<String, java.util.List<ImageIcon>> imagenesPorEtiqueta = agruparImagenesPorEtiqueta();
        java.util.List<ImageIcon> tarjetasParaJuego = seleccionarParejasAleatorias(imagenesPorEtiqueta, 9); // 9 parejas = 18 tarjetas

        // Mezclar las tarjetas
        Collections.shuffle(tarjetasParaJuego);

        // Crear botones para cada tarjeta
        for (ImageIcon imagen : tarjetasParaJuego) {
            JButton tarjeta = new JButton();
            tarjeta.setPreferredSize(new Dimension(120, 150));
            tarjeta.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

            // Asignar imagen oculta (cubierta)
            ImageIcon cubierta = new ImageIcon("ruta/a/cubierta.jpg");
            tarjeta.setIcon(cubierta);

            // Guardar la imagen real como propiedad del botón
            tarjeta.putClientProperty("imagen", imagen);

            // ActionListener para voltear la tarjeta
            tarjeta.addActionListener(e -> {
                if (bloqueado) return; // No hacer nada si ya hay una verificación en curso
                JButton btn = (JButton) e.getSource();

                // Si la carta ya está volteada o ya fue emparejada, no hacer nada
                if (btn.getIcon() != cubierta || !btn.isEnabled()) return;

                // Voltear la carta
                ImageIcon imagenReal = (ImageIcon) btn.getClientProperty("imagen");
                btn.setIcon(imagenReal);

                // Registrar la selección
                if (primeraSeleccion == null) {
                    primeraSeleccion = btn;
                } else if (segundaSeleccion == null && btn != primeraSeleccion) {
                    segundaSeleccion = btn;
                    bloqueado = true; // Bloquear interacciones mientras verificamos

                    // Verificar si es un par después de un breve retraso (sin usar Timer)
                    new Thread(() -> {
                        try {
                            Thread.sleep(500); // Pequeña pausa para que el jugador vea las cartas
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                        SwingUtilities.invokeLater(() -> {
                            verificarPar();
                            bloqueado = false;
                        });
                    }).start();
                }
            });

            panelTarjetas.add(tarjeta);
        }

        backgroundPanel.add(panelTarjetas, BorderLayout.CENTER);
        backgroundPanel.add(panelJugadores, BorderLayout.NORTH);
        juegoFrame.add(backgroundPanel);
        juegoFrame.setVisible(true);
    }
    private Map<String, java.util.List<ImageIcon>> agruparImagenesPorEtiqueta() {
        Map<String, java.util.List<ImageIcon>> mapa = new HashMap<>();
        String[] nombresImagenes = {
                "albino_tigre.jpg", "artico_lobo.jpg", "babuino_mono.jpg",
                "bengala_tigre.jpg", "calva_aguila.jpg", "capuchino_mono.jpg",
                "cascabel_serpiente.jpg", "emperador_pinguino.jpg", "gris_lobo.jpg",
                "humboldt_pinguino.jpg", "narizbotella_delfin.jpg", "pardo_oso.jpg",
                "piton_serpiente.jpg", "polar_oso.jpg", "real_aguila.jpg",
                "rosa_delfin.jpg", "tarjeta_comodin.jpg", "tarjeta_trampa.jpg"
        };

        for (String nombre : nombresImagenes) {
            try {
                String etiqueta = nombre.split("_")[1].replace(".jpg", "");

                // Cargar imagen
                ImageIcon icono = new ImageIcon("C:\\Users\\Usuario\\IdeaProjects\\Practica7\\imagenes\\animales\\" + nombre);
                // Establecer la descripción con el nombre del archivo
                icono.setDescription(nombre);

                Image img = icono.getImage().getScaledInstance(120, 150, Image.SCALE_SMOOTH);
                ImageIcon iconoRedimensionado = new ImageIcon(img);
                // También establecer la descripción para el icono redimensionado
                iconoRedimensionado.setDescription(nombre);

                // Agregar al mapa
                mapa.computeIfAbsent(etiqueta, k -> new ArrayList<>()).add(iconoRedimensionado);
            } catch (Exception e) {
                System.err.println("Error al cargar: " + nombre);
            }
        }

        return mapa;
    }

    private List<ImageIcon> seleccionarParejasAleatorias(Map<String, List<ImageIcon>> imagenesPorEtiqueta, int nParejas) {
        List<ImageIcon> tarjetas = new ArrayList<>();
        List<String> etiquetas = new ArrayList<>(imagenesPorEtiqueta.keySet());

        // Evitar etiquetas con menos de 2 imágenes (ej: comodín/trampa)
        etiquetas.removeIf(etiqueta -> imagenesPorEtiqueta.get(etiqueta).size() < 2);

        // Mezclar etiquetas y seleccionar las primeras 'nParejas'
        Collections.shuffle(etiquetas);
        for (int i = 0; i < Math.min(nParejas, etiquetas.size()); i++) {
            String etiqueta = etiquetas.get(i);
            List<ImageIcon> imagenes = imagenesPorEtiqueta.get(etiqueta);
            tarjetas.add(imagenes.get(0)); // Primera imagen
            tarjetas.add(imagenes.get(1)); // Segunda imagen (pareja)
        }

        return tarjetas;
    }
    }




