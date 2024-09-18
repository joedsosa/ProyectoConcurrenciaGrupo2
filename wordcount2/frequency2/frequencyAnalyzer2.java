import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Rank {
    String word;
    String word2;
    int number;

    public Rank(String word, String word2, int number) {
        this.word = word;
        this.word2 = word2;
        this.number = number;
    }

    public String getWord() {
        return word;
    }

    public String getWord2() {
        return word2;
    }

    public int getNumber() {
        return number;
    }
}

public class frequencyAnalyzer2 {

    public static void main(String[] args) {
        List<Rank> myList = new ArrayList<>();

        // Abre el archivo para lectura
        try (BufferedReader br = new BufferedReader(new FileReader("part-r-00000"))) {
            String line;
            // Lee el archivo línea por línea
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                // Verifica que haya al menos tres palabras en la línea
                if (words.length >= 3) {
                    String firstWord = words[0];
                    String secondWord = words[1];
                    try {
                        int number = Integer.parseInt(words[2]);
                        // Filtra y guarda los datos si el número es mayor o igual a 5000
                        if (number >= 5000) {
                            myList.add(new Rank(firstWord, secondWord, number));
                            // Guarda el resultado en el archivo
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter("resultado2.txt", true))) {
                                bw.write(firstWord + " " + secondWord + " " + number);
                                bw.newLine();
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Advertencia: '" + words[2] + "' no es un número entero.");
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
                bw.write(rank.getWord() + " " + rank.getWord2() + " " + rank.getNumber());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al escribir en 'resultado-ordenado.txt'.");
        }
    }
}
