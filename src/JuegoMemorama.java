import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class JuegoMemorama {
    private Tarjeta[][] tablero;
    private int filas = 3;
    private int columnas = 6;
    private Tarjeta primeraSeleccion;
    private Tarjeta segundaSeleccion;
    private boolean tableroHabilitado;
    private int parejasEncontradas;
    private int intentosRealizados;
    private String modoJuego;

    /**
     * Constructor de la clase JuegoMemorama
     * Inicializa un tablero de 3x6 con el modo de juego especificado
     * @param modoJuego El modo de juego: "animales", "deportistas", "instrumentos"
     */
    public JuegoMemorama(String modoJuego) {
        tablero = new Tarjeta[filas][columnas];
        this.modoJuego = modoJuego;
        primeraSeleccion = null;
        segundaSeleccion = null;
        tableroHabilitado = true;
        parejasEncontradas = 0;
        intentosRealizados = 0;

        // Inicializa el tablero con las tarjetas según el modo de juego
        inicializarTablero(crearTarjetas(modoJuego));
    }

    /**
     * Inicializa el tablero con tarjetas
     * @param tarjetas Lista de tarjetas para el juego (debe contener 18 tarjetas)
     * @return true si la inicialización fue exitosa, false en caso contrario
     */
    public boolean inicializarTablero(ArrayList<Tarjeta> tarjetas) {
        if (tarjetas.size() != filas * columnas) {
            return false;
        }

        Collections.shuffle(tarjetas); // Mezclamos las tarjetas

        int indice = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = tarjetas.get(indice++);
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

        Tarjeta tarjetaSeleccionada = tablero[fila][columna];

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
            return primeraSeleccion.getIdentificador().equals(segundaSeleccion.getIdentificador());
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


    public Tarjeta getTarjeta(int fila, int columna) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return null;
        }

        return tablero[fila][columna];
    }

    public ArrayList<Tarjeta> crearTarjetas(String modoJuego) {
        switch(modoJuego.toLowerCase()) {
            case "animales":
                return crearTarjetasAnimales();
            case "deportistas":
                return crearTarjetasDeportistas();
            case "instrumentos":
                return crearTarjetasInstrumentos();
            default:
                return crearTarjetasAnimales(); // Modo por defecto
        }
    }

    /**
     * Crea un conjunto de tarjetas de animales
     * @return ArrayList de tarjetas de animales
     */
    private ArrayList<Tarjeta> crearTarjetasAnimales() {
        TarjetaAnimal.establecerParejaEspecial("animales");

        ArrayList<Tarjeta> tarjetas = new ArrayList<>();

        // Define tipos de animales para el juego (necesitamos 9 tipos para 18 tarjetas)
        String[] tiposAnimales = {
                "tigre", "lobo", "aguila", "delfin", "oso",
                "serpiente", "pinguino", "mono", "perro"
        };

        String[] tiposParejas = {
                "albino", "bengala", "artico", "gris", "calva", "real", "rosa", "narizbotella", "pardo", "polar",
                "piton", "cascabel", "emperador", "humboldt", "capuchino", "babuino", "chihuahua", "bulldog"
        };

        for (int i = 0; i < tiposAnimales.length; i++) {
            String rutaImagen1 = tiposParejas[2*i] + "_" + tiposAnimales[i] + ".jpg";
            String rutaImagen2 = tiposParejas[2*i+1] + "_" + tiposAnimales[i] + ".jpg";

            tarjetas.add(new TarjetaAnimal(rutaImagen1, tiposAnimales[i]));
            tarjetas.add(new TarjetaAnimal(rutaImagen2, tiposAnimales[i]));
        }

        return tarjetas;
    }

    /**
     * Crea un conjunto de tarjetas de deportistas
     * @return ArrayList de tarjetas de deportistas
     */
    private ArrayList<Tarjeta> crearTarjetasDeportistas() {
        ArrayList<Tarjeta> tarjetas = new ArrayList<>();

        // Define países y sus deportistas (9 países para 18 tarjetas)
        String[] paises = {
                "alemania", "argentina", "brasil", "corea", "españa",
                "mexico", "portugal", "usa", "polonia"
        };

        String[] deportistas = {
                "bandera_alemania", "kroos_alemania",
                "bandera_argentina", "messi_argentina",
                "bandera_brasil", "neymar_brasil",
                "bandera_corea", "son_corea",
                "bandera_españa", "iniesta_españa",
                "bandera_mexico", "chicharito_mexico",
                "bandera_portugal", "cristiano_portugal",
                "bandera_usa", "pulisic_usa",
                "bandera_polonia","lewandoski_polonia",
        };

        for (int i = 0; i < paises.length; i++) {
            String rutaImagen1 = deportistas[2*i] + ".png";
            String rutaImagen2 = deportistas[2*i+1] + ".png";

            tarjetas.add(new TarjetaDeportista(rutaImagen1, paises[i]));
            tarjetas.add(new TarjetaDeportista(rutaImagen2, paises[i]));
        }

        return tarjetas;
    }

    private ArrayList<Tarjeta> crearTarjetasInstrumentos() {
        ArrayList<Tarjeta> tarjetas = new ArrayList<>();

        String[] tiposInstrumentos = {
                "saxofon", "clarinete", "cuerda", "bajo", "flauta",
                "guitarra", "percusion", "boquilla", "orquesta"
        };

        String[] instrumentos = {
                "alto_saxofon", "tenor_saxofon",
                "bajo_clarinete", "normal_clarinete",
                "chelo_cuerda", "violin_cuerda",
                "guitarron_bajo", "normal_bajo",
                "normal_flauta", "piccolo_flauta",
                "normal_guitarra", "requinto_guitarra",
                "tarola_percusion", "xilo_percusion",
                "trombon_boquilla", "trompeta_boquilla",
                "director_orquesta", "orquesta_orquesta"
        };

        for (int i = 0; i < tiposInstrumentos.length; i++) {
            String rutaImagen1 = instrumentos[2*i] + ".png";
            String rutaImagen2 = instrumentos[2*i+1] + ".png";

            tarjetas.add(new TarjetaInstrumento(rutaImagen1, tiposInstrumentos[i]));
            tarjetas.add(new TarjetaInstrumento(rutaImagen2, tiposInstrumentos[i]));

            System.out.println(rutaImagen1);
            System.out.println(rutaImagen2);
        }

        return tarjetas;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public boolean isTableroHabilitado() {
        return tableroHabilitado;
    }

    public void setTableroHabilitado(boolean habilitado) {
        this.tableroHabilitado = habilitado;
    }

    public String getModoJuego() {
        return modoJuego;
    }
}