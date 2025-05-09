public class TarjetaTrampa extends Tarjeta {
    public TarjetaTrampa(String rutaImagen, String identificador) {
        super(rutaImagen, identificador);
    }

    public void activarEfectoEspecial(){
        // Futura implementación de efecto negativo por seleccionar esta tarjeta
        System.out.println("Tarjeta trampa activada");
    }

    public boolean tieneEfectoEspecial(){
        return true;
    }

    public boolean esPareja(Tarjeta otraTarjeta) {
        // Las Tarjetas trampa no tienen pareja
        return false;
    }
}
