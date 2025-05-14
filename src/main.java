public class main {
    public static void main(String[] args) {
        TarjetaNormal[] tarjetas = new TarjetaNormal[16];
        JuegoMemorama juego = new JuegoMemorama();
        String[] nombresJugadores = {"Ana", "Carlos"};
        int[] puntuacionesIniciales = {0, 0};
        tarjetas = JuegoMemorama.crearTarjetasAnimales();
        for (int i = 0; i < tarjetas.length; i++) {
            System.out.println(tarjetas[i]);
        }
        TableroMemorama tablero = new TableroMemorama(tarjetas,"animales",nombresJugadores,puntuacionesIniciales);
    }
}
