public class JuegoMemorama {
    private TarjetaNormal[][] tablero;
    private int filas = 3;
    private int columnas = 6;
    private TarjetaNormal primeraSeleccion;
    private TarjetaNormal segundaSeleccion;
    private boolean tableroHabilitado;
    private int parejasEncontradas;
    private int intentosRealizados;


    /**
     * Constructor de la clase JuegoMemorama
     * Inicializa un tablero de 3x6
     */
    public JuegoMemorama() {
        tablero = new TarjetaNormal[filas][columnas];
        primeraSeleccion = null;
        segundaSeleccion = null;
        tableroHabilitado = true;
        parejasEncontradas = 0;
        intentosRealizados = 0;
    }

    /**
     * Inicializa el tablero con tarjetas
     * @param tarjetas Lista de tarjetas para el juego (debe contener 18 tarjetas)
     * @return true si la inicialización fue exitosa, false en caso contrario
     */
    public boolean inicializarTablero(TarjetaNormal[] tarjetas) {
        if (tarjetas.length != filas * columnas) {
            return false;
        }

        int indice = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = tarjetas[indice++];
            }
        }

        primeraSeleccion = null;
        segundaSeleccion = null;
        tableroHabilitado = true;
        parejasEncontradas = 0;
        intentosRealizados = 0;

        return true;
    }

    /**
     * Selecciona una tarjeta en las coordenadas proporcionadas
     * @param fila Fila de la tarjeta
     * @param columna Columna de la tarjeta
     * @return Estado de selección: 0=inválida, 1=primera selección, 2=segunda selección
     */
    public int seleccionarTarjeta(int fila, int columna) {
        // Verificar si el tablero está habilitado
        if (!tableroHabilitado) {
            return 0;
        }

        // Verificar límites
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return 0;
        }

        TarjetaNormal tarjetaSeleccionada = tablero[fila][columna];

        // Verificar si la tarjeta ya está descubierta o tiene pareja
        if (tarjetaSeleccionada.estaDescubierta() || tarjetaSeleccionada.tienePareja()) {
            return 0;
        }

        // Voltear la tarjeta
        tarjetaSeleccionada.voltearTarjeta();

        // Primera selección
        if (primeraSeleccion == null) {
            primeraSeleccion = tarjetaSeleccionada;
            return 1;
        }

        // Segunda selección
        segundaSeleccion = tarjetaSeleccionada;
        intentosRealizados++;

        return 2;
    }

    /**
     * Verifica si las tarjetas seleccionadas forman una pareja
     * @return true si son pareja, false en caso contrario
     */
    public boolean verificarPareja() {
        if (primeraSeleccion != null && segundaSeleccion != null) {
            return primeraSeleccion.esPareja(segundaSeleccion);
        }
        return false;
    }

    /**
     * Procesa el resultado después de seleccionar dos tarjetas
     * @return true si son pareja, false en caso contrario
     */
    public boolean procesarSeleccion() {
        if (primeraSeleccion == null || segundaSeleccion == null) {
            return false;
        }

        boolean sonPareja = verificarPareja();

        if (sonPareja) {
            // Marcar como pareja
            primeraSeleccion.marcarPareja();
            segundaSeleccion.marcarPareja();
            parejasEncontradas++;

            // Reiniciar selecciones
            primeraSeleccion = null;
            segundaSeleccion = null;
        } else {
            // No son pareja, las tarjetas se mantendrán volteadas hasta que el usuario
            // llame a voltearTarjetasNoEmparejadas()
            tableroHabilitado = false;
        }

        return sonPareja;
    }

    /**
     * Voltea las tarjetas que no formaron pareja
     */
    public void voltearTarjetasNoEmparejadas() {
        if (primeraSeleccion != null && !primeraSeleccion.tienePareja()) {
            primeraSeleccion.voltearTarjeta();
        }

        if (segundaSeleccion != null && !segundaSeleccion.tienePareja()) {
            segundaSeleccion.voltearTarjeta();
        }

        primeraSeleccion = null;
        segundaSeleccion = null;
        tableroHabilitado = true;
    }

    /**
     * Verifica si todas las parejas han sido encontradas
     * @return true si el juego está completado, false en caso contrario
     */
    public boolean juegoCompletado() {
        return parejasEncontradas == (filas * columnas) / 2;
    }

    /**
     * Obtiene el número de intentos realizados
     * @return Número de intentos
     */
    public int getIntentos() {
        return intentosRealizados;
    }

    /**
     * Obtiene el número de parejas encontradas
     * @return Número de parejas encontradas
     */
    public int getParejasEncontradas() {
        return parejasEncontradas;
    }

    /**
     * Obtiene la tarjeta en la posición indicada
     * @param fila Fila de la tarjeta
     * @param columna Columna de la tarjeta
     * @return La tarjeta en esa posición
     */
    public TarjetaNormal getTarjeta(int fila, int columna) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return null;
        }

        return tablero[fila][columna];
    }

    /**
     * Crea un conjunto de tarjetas para un juego de 3x6 con tipos de animales
     * @return Arreglo de tarjetas para el juego
     */
    public static TarjetaNormal[] crearTarjetasAnimales() {
        // Define tipos de animales para el juego (necesitamos 9 tipos para 18 tarjetas)
        String[] tiposAnimales = {
                "tigre","lobo","aguila", "delfin","oso", "serpiente","pinguino",
                "mono"
        };

        String[] tiposParejas = {
                "albino","bengala","artico","gris","calva","real","rosa","narizbotella","pardo","polar",
                "piton","cascabel","emperador","humboldt","capuchino","babuino"
        };

        TarjetaNormal[] tarjetas = new TarjetaNormal[16];

        int index = 0;
        for (int i = 0; i < tiposAnimales.length; i++) {

            String rutaImagen1 = tiposParejas[2*i] + "_" + tiposAnimales[i] + ".jpg";
            String rutaImagen2 = tiposParejas[2*i+1] + "_" + tiposAnimales[i] + ".jpg";

            tarjetas[index++] = new TarjetaNormal(rutaImagen1,tiposAnimales[i]);
            tarjetas[index++] = new TarjetaNormal(rutaImagen2,tiposAnimales[i]);

            System.out.println("Tarjeta " + (index-1) + ": " + tiposAnimales[i] + " - " + rutaImagen1);
            System.out.println("Tarjeta " + (index) + ": " + tiposAnimales[i] + " - " + rutaImagen2);
        }



        // Mezclar las tXarjetas
        mezclarTarjetas(tarjetas);

        return tarjetas;
    }

    public static void mezclarTarjetas(TarjetaNormal[] tarjetas) {
        java.util.Random rnd = new java.util.Random();

        for (int i = tarjetas.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);

            // Intercambiar
            TarjetaNormal temp = tarjetas[index];
            tarjetas[index] = tarjetas[i];
            tarjetas[i] = temp;
        }
    }

    /**
     * Obtiene el número de filas del tablero
     * @return Número de filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * Obtiene el número de columnas del tablero
     * @return Número de columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Verifica si el tablero está habilitado para seleccionar tarjetas
     * @return true si está habilitado, false en caso contrario
     */
    public boolean isTableroHabilitado() {
        return tableroHabilitado;
    }

    /**
     * Habilita o deshabilita el tablero
     * @param habilitado Estado de habilitación
     */
    public void setTableroHabilitado(boolean habilitado) {
        this.tableroHabilitado = habilitado;
    }
}