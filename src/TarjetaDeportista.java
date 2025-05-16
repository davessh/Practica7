import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

class TarjetaDeportista extends Tarjeta {
    private String pais;
    private static final String RUTA_SONIDO = "C:\\Users\\Usuario\\IdeaProjects\\Practica7.2\\sonidos\\gol.wav";
    public TarjetaDeportista(String rutaImagen, String pais) {
        super(rutaImagen, pais);
        this.pais = pais;
    }

    @Override
    public boolean esPareja(Tarjeta otraTarjeta) {
        if (!(otraTarjeta instanceof TarjetaDeportista)) {
            return false;
        }

        TarjetaDeportista otra = (TarjetaDeportista) otraTarjeta;

        // Dos tarjetas son pareja si:
        // 1. Son del mismo país
        // 2. No son la misma tarjeta (rutas diferentes)
        return this.pais.equals(otra.pais) &&
                !this.rutaImagen.equals(otra.rutaImagen);
    }

    @Override
    public boolean tieneEfectoEspecial() {
        return true;
    }

    @Override
    public void activarEfectoEspecial() {
        if (tieneEfectoEspecial()) {
            try {
                File archivoSonido = new File(RUTA_SONIDO);
                if (archivoSonido.exists()) {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoSonido);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                }
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error al reproducir el sonido: " + e.getMessage());
            }
        }
    }
    }
