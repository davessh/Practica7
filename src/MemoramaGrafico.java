import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MemoramaGrafico {

    private JFrame frame;
    private JuegoMemorama juego;
    private JButton botonJugar, botonSalir;
    public MemoramaGrafico(int cantidadDeJugadores) {
        this.juego = new JuegoMemorama(cantidadDeJugadores);
        empezarJuego();
    }

    public void empezarJuego() {
        frame = new JFrame("Memorama");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage;

            {
                try {
                    backgroundImage = new ImageIcon("C:\\Users\\GF76\\IdeaProjects\\Practica-7\\src\\MEMORAMA.png").getImage();
                    backgroundImage = backgroundImage.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, this);
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
        panelMenu.add(botonJugar);

        panelMenu.add(Box.createVerticalStrut(10)); // Espacio entre botones

        botonSalir = new JButton("Salir");
        botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSalir.setMaximumSize(new Dimension(250, 55));
        botonSalir.setFont(new Font("Century Gothic", Font.BOLD, 25));
        panelMenu.add(botonSalir);

        panelMenu.add(Box.createVerticalGlue());

        botonSalir.addActionListener(e -> System.exit(0));
        botonJugar.addActionListener(e -> {
            // Acción para jugar
        });

        backgroundPanel.add(panelMenu, BorderLayout.CENTER);
        frame.add(backgroundPanel);
        frame.setVisible(true);
    }

    public void seleccionarModoDeJuego(){

    }
    }


