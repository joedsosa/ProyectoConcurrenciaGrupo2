import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class preProcesamiento {

    public static void main(String[] args) {
        String archivoDataset = "archivosImportantes/dataset.txt";
        String archivoDiccionario = "archivosImportantes/Diccionario.txt";
        String archivoSalida = "archivosImportantes/datasetProcesado.txt";
        limpiarDataset(archivoDataset, archivoDiccionario, archivoSalida);
    }

    private static String eliminarPuntuacion(String linea) {
        return linea.replaceAll("&'.*?\\w+;", " ")
                    .replaceAll("[^a-zA-Z ]", " ")
                    .replaceAll("\\s+", " ");
    }

    private static ArrayList<String> cargarDiccionario(String archivoDiccionario) {
        ArrayList<String> diccionario = new ArrayList<>();
        try (BufferedReader lectorDiccionario = new BufferedReader(new FileReader(archivoDiccionario))) {
            String palabra;
            while ((palabra = lectorDiccionario.readLine()) != null) {
                diccionario.add(palabra.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
        return diccionario;
    }

    public static void limpiarDataset(String archivoDataset, String archivoDiccionario, String archivoSalida) {
        try {
            ArrayList<String> diccionario = cargarDiccionario(archivoDiccionario);

            try (BufferedReader br = new BufferedReader(new FileReader(archivoDataset));
                    FileWriter fw = new FileWriter(archivoSalida, true)) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    linea = eliminarPuntuacion(linea);
                    String[] palabras = linea.split("\\s+");
                    for (String palabra : palabras) {
                        String palabraLimpiada = palabra.toLowerCase();
                        if (!diccionario.contains(palabraLimpiada)) {
                            fw.write(palabraLimpiada + " ");
                        }
                    }
                    fw.write("\n");
                }
            }
            System.out.println("Dataset limpiado exitosamente y guardado en " + archivoSalida);
        } catch (IOException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }

}

