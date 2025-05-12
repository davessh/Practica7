import javax.swing.*;
import java.util.*;

public class JuegoMemorama {
    private int cantidadJugadores;
    private int[] puntajes;
    private int turnoActual;

    public JuegoMemorama(int cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
        this.puntajes = new int[cantidadJugadores];
        this.turnoActual = 0;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public void siguienteTurno() {
        turnoActual = (turnoActual + 1) % cantidadJugadores;
    }

    public void sumarPunto() {
        puntajes[turnoActual]++;
    }

    public int[] getPuntajes() {
        return puntajes;
    }
}
