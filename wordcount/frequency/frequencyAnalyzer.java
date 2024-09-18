import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Rank {
    String word;
    int number;

    public Rank(String word, int number) {
        this.word = word;
        this.number = number;
    }

    public String getWord() {
        return word;
    }

    public int getNumber() {
        return number;
    }
}

public class frequencyAnalyzer {

    public static void main(String[] args) {
        List<Rank> myList = new ArrayList<>();

        // Abre el archivo para lectura
        try (BufferedReader br = new BufferedReader(new FileReader("part-r-00000"))) {
            String line;
            // Lee el archivo línea por línea
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                // Verifica que haya al menos dos palabras en la línea
                if (words.length >= 2) {
                    String firstWord = words[0];
                    try {
                        int secondWord = Integer.parseInt(words[1]);
                        // Filtra y guarda los datos si el número es mayor o igual a 5000
                        if (secondWord >= 5000) {
                            myList.add(new Rank(firstWord, secondWord));
                            // Guarda el resultado en el archivo
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter("resultado.txt", true))) {
                                bw.write(firstWord + " " + secondWord);
                                bw.newLine();
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Advertencia: '" + words[1] + "' no es un número entero.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error: No se pudo abrir el archivo 'part-r-00000'.");
        }

        // Ordena la lista por el número de manera descendente
        myList.sort(Comparator.comparingInt(Rank::getNumber).reversed());

        // Guarda los resultados ordenados en otro archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resultado-ordenado.txt"))) {
            for (Rank rank : myList) {
                bw.write(rank.getWord() + " " + rank.getNumber());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al escribir en 'resultado-ordenado.txt'.");
        }
    }
}
