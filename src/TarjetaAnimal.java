import javax.swing.*;
import java.util.Random;

public class TarjetaAnimal extends Tarjeta {
    private String tipoEmparejamiento;
    private boolean esEspecial;
    private boolean efectoActivado = false;
    private TableroMemorama tablero;
    private static String parejaEspecial;
    public static void establecerParejaEspecial(String modoJuego) {
        if (modoJuego.equalsIgnoreCase("animales")) {
            String[] animalesEspeciales = {"tigre", "lobo", "aguila", "delfin", "oso",
                    "serpiente", "pinguino", "mono"};
            Random rand = new Random();
            parejaEspecial = animalesEspeciales[rand.nextInt(animalesEspeciales.length)];
        }
    }

    // Método para obtener el identificador de la pareja especial
    public static String getParejaEspecial() {
        return parejaEspecial;
    }

    public TarjetaAnimal(String rutaImagen, String identificador) {
        super(rutaImagen, identificador);
        this.tipoEmparejamiento = identificador;
        esEspecial = rutaImagen.contains("comodin") || rutaImagen.contains("trampa");
    }

    public void setTablero(TableroMemorama tablero) {
        this.tablero = tablero;
    }

    @Override
    public boolean esPareja(Tarjeta otraTarjeta) {
        return true;
    }

    @Override
    public boolean tieneEfectoEspecial() {
        return true;
    }
    //Se incluye la logica para la pareja especial
    @Override
    public void activarEfectoEspecial() {
        if (efectoActivado) return;

        if (this.getIdentificador().equals(parejaEspecial)) {
            if (tablero != null) {
                tablero.sumarPuntoJugadorActual();
                JOptionPane.showMessageDialog(tablero,
                        "¡Pareja especial encontrada! +2 puntos",
                        "Pareja Especial",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

        efectoActivado = true;
    }
    }
