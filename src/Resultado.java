public class Resultado {

    private Circuito circuito;
    private Piloto piloto;
    private int posicion; // Si un piloto no acaba, su posición final será -1
    private double puntos;

    // Getters
    public Circuito getCircuito() {
        return circuito;
    }
    public Piloto getPiloto() {
        return piloto;
    }
    public int getPosicion() {
        return posicion;
    }
    public double getPuntos() {
        return puntos;
    }

    // Setters
    public void setCircuito(Circuito circuito) { this.circuito = circuito; }
    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    public void setPuntos(double puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "Resultado{" +
                "circuito='" + circuito.toString() + '\'' +
                ", piloto='" + piloto.toString() + '\'' +
                ", posicion=" + posicion +
                ", puntos=" + puntos +
                '}';
    }
}
