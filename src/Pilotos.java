import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;

@XmlRootElement(name="drivers")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pilotos {
    @XmlElement(name="driver")
    private ArrayList<Piloto> pilotos = null;

    public ArrayList<Piloto> getPilotos() {
        return pilotos;
    }

    public void setPilotos(ArrayList<Piloto> pilotos) {
        this.pilotos = pilotos;
    }
}