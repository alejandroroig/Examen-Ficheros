import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mundial {
    private static final String COMMA_DELIMITER = ",";

    public static List<Piloto> leerPilotos() {
        Path ficheroXML = Path.of("data", "pilotos.xml");
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(Pilotos.class);
            Unmarshaller jaxbUnmarshaller  = context.createUnmarshaller();

            Pilotos pilotos = (Pilotos) jaxbUnmarshaller.unmarshal(ficheroXML.toFile());
            return pilotos.getPilotos();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<Circuito> leerCircuitos() {
        Path ficheroCSV = Path.of("data", "circuitos.csv");
        List<Circuito> circuitos = new ArrayList<>();

        try (Stream<String> lineasFichero = Files.lines(ficheroCSV).skip(1)) {
            for (String[] arrayLineaCircuito : lineasFichero.map(l -> l.split(COMMA_DELIMITER)).toList()) {
                Circuito circuito = new Circuito();
                circuito.setRonda(Integer.parseInt(arrayLineaCircuito[0]));
                circuito.setPais(arrayLineaCircuito[1]);
                circuito.setFechaCarrera(LocalDate.parse(arrayLineaCircuito[2], DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)));
                circuitos.add(circuito);
            }
            return circuitos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Resultado> leerResultados(List<Circuito> circuitos, List<Piloto> pilotos) {
        Path ficheroJSON = Path.of("data", "resultados.json");
        List<Resultado> resultados = new ArrayList<>();

        try {
            String datosFichero = Files.readString(ficheroJSON);
            JSONArray jsonArray = new JSONArray(datosFichero);

            Map<String, Piloto> mapaPilotos = pilotos.stream().collect(Collectors.toMap(Piloto::getNombre, p -> p));
            Map<String, Circuito> mapaCircuitos = circuitos.stream().collect(Collectors.toMap(Circuito::getPais, c -> c));

            for (int iObj = 0; iObj < jsonArray.length(); iObj++) {
                JSONObject jsonResultado = new JSONObject(jsonArray.get(iObj).toString());
                Resultado resultado = new Resultado();

                resultado.setPiloto(mapaPilotos.get(jsonResultado.getString("Driver")));
                resultado.setCircuito(mapaCircuitos.get(jsonResultado.getString("Track")));

                try {
                    resultado.setPosicion(jsonResultado.getInt("Position"));
                } catch (Exception e) {
                    resultado.setPosicion(-1);
                }
                resultado.setPuntos(jsonResultado.getDouble("Points"));
                resultados.add(resultado);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultados;

    }

    public static void imprimirClasificacionFinal(List<Resultado> resultados) {
        System.out.println("\n--- Clasificación del mundial de pilotos 2019 ---");
        resultados.stream()
                .collect(Collectors.groupingBy(p -> p.getPiloto().getNombre(), Collectors.summingDouble(Resultado::getPuntos)))
                .entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(p -> System.out.println(p.getKey() + " : " + p.getValue() + " puntos"));
    }

    public static void imprimirMayoresde30(List<Piloto> pilotos) {
        System.out.println("\n--- Pilotos mayores de 30 años ---");
        pilotos.stream()
                .filter(p -> p.getEdad() >= 30)
                .sorted(Comparator.comparingInt(Piloto::getEdad).reversed())
                .forEach(p-> System.out.println(p.getNombre() + " tiene " + p.getEdad() + " años"));
    }

    public static void main(String[] args) {
        List<Piloto> pilotos = leerPilotos();
        List<Circuito> circuitos = leerCircuitos();

        List<Resultado> resultados = leerResultados(circuitos, pilotos);

        imprimirClasificacionFinal(resultados);

        imprimirMayoresde30(pilotos);
    }
}
