import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Circuito {
    private int ronda;
    private String pais;
    private LocalDate fechaCarrera;

    // Getters
    public int getRonda() {
        return ronda;
    }
    public String getPais() {
        return pais;
    }
    public LocalDate getFechaCarrera() {
        return fechaCarrera;
    }

    // Setters
    public void setRonda(int ronda) {
        this.ronda = ronda;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public void setFechaCarrera(LocalDate fechaCarrera) {
        this.fechaCarrera = fechaCarrera;
    }

    @Override
    public String toString() {
        return "Circuito{" +
                "ronda=" + ronda +
                ", pais='" + pais + '\'' +
                ", fechaCarrera=" + fechaCarrera.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) +
                '}';
    }
}
