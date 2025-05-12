class TarjetaTrampa extends Tarjeta {
    public TarjetaTrampa(String rutaImagen, String identificador) {
        super(rutaImagen, identificador);
    }

    @Override
    public boolean tieneEfectoEspecial() {
        return true;
    }

    @Override
    public void activarEfectoEspecial() {
        // Futura implementación de efecto negativo por seleccionar esta tarjeta
        System.out.println("Tarjeta trampa activada");
    }

    @Override
    public boolean esPareja(Tarjeta otraTarjeta) {
        // Las Tarjetas trampa no tienen pareja
        return false;
    }
}