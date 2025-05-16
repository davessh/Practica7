import javax.swing.*;
import java.awt.*;

class TarjetaInstrumento extends Tarjeta {
    private String tipoInstrumento; // El identificador es el tipo de instrumento
    private TableroMemorama tablero; // Referencia al tablero para acceder a los botones
    private static final String RUTA_GIF = "C:\\Users\\Usuario\\IdeaProjects\\Practica7.2\\imagenes\\instrumentos\\efecto_especial.gif";

    public TarjetaInstrumento(String rutaImagen, String tipoInstrumento) {
        super(rutaImagen, tipoInstrumento);
        this.tipoInstrumento = tipoInstrumento;
    }

    // Método para establecer la referencia al tablero
    public void setTablero(TableroMemorama tablero) {
        this.tablero = tablero;
    }

    @Override
    public boolean esPareja(Tarjeta otraTarjeta) {
        if (!(otraTarjeta instanceof TarjetaInstrumento)) {
            return false;
        }

        TarjetaInstrumento otra = (TarjetaInstrumento) otraTarjeta;
        return this.tipoInstrumento.equals(otra.tipoInstrumento) &&
                !this.rutaImagen.equals(otra.rutaImagen);
    }

    @Override
    public boolean tieneEfectoEspecial() {
        // Todos los instrumentos tienen efecto especial (mostrar GIF)
        return true;
    }

    @Override
    public void activarEfectoEspecial() {
        if (tablero != null) {
            // Mostrar el GIF en ambas tarjetas de la pareja
            mostrarGifTemporal();
        }
    }

    private void mostrarGifTemporal() {
        // Cargar el GIF
        ImageIcon gifIcon = new ImageIcon(RUTA_GIF);

        // Obtener las coordenadas de esta tarjeta en el tablero
        int[] coordenadas = obtenerCoordenadasEnTablero();
        if (coordenadas == null) return;

        int fila = coordenadas[0];
        int columna = coordenadas[1];

        // Mostrar el GIF en el botón correspondiente
        JButton boton = tablero.getBotones()[fila][columna];
        boton.setIcon(gifIcon);

        // Configurar un temporizador para volver a la imagen original después de 1 segundo
        Timer timer = new Timer(1000, e -> {
            // Volver a mostrar la imagen original
            String rutaImagen = tablero.getRUTA_BASE() + "instrumentos\\" + this.rutaImagen;
            ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
            Image img = iconoOriginal.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        });
        timer.setRepeats(false);
        timer.start();
    }

    private int[] obtenerCoordenadasEnTablero() {
        if (tablero == null) return null;

        JuegoMemorama juego = tablero.getJuego();
        for (int i = 0; i < juego.getFilas(); i++) {
            for (int j = 0; j < juego.getColumnas(); j++) {
                Tarjeta tarjeta = juego.getTarjeta(i, j);
                if (tarjeta == this) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}