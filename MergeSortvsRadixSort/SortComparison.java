import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class SortComparison {

    public static void main(String[] args) throws IOException {
        // Leitura do conteúdo do arquivo em uma única String
        String content = new String(Files.readAllBytes(Paths.get("dados100_mil.txt")));
        
        // Remoção dos colchetes no início e no final da String, se presentes
        content = content.trim(); // Remove espaços em branco no início e no final
        if (content.startsWith("[")) {
            content = content.substring(1);
        }
        if (content.endsWith("]")) {
            content = content.substring(0, content.length() - 1);
        }
        
        // Divisão da string em várias, usando a vírgula como separador
        String[] numbersAsString = content.split(",\\s*");
        
        // Conversão de cada string em um inteiro
        int[] numbers = Arrays.stream(numbersAsString).mapToInt(Integer::parseInt).toArray();

        // As operações de ordenação seguem aqui...
        
        int[] numbersForMergeSort = numbers.clone();
        int[] numbersForRadixSort = numbers.clone();

        long start = System.currentTimeMillis();
        MergeSort.sort(numbersForMergeSort);
        long end = System.currentTimeMillis();
        System.out.println("Merge Sort: " + formatTime(end - start));

        start = System.currentTimeMillis();
        RadixSortMSD.sort(numbersForRadixSort);
        end = System.currentTimeMillis();
        System.out.println("Radix Sort (MSD): " + formatTime(end - start));
    }

    private static String formatTime(long millis) {
        long hours = java.util.concurrent.TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(millis) -
                java.util.concurrent.TimeUnit.HOURS.toMinutes(hours);
        long seconds = java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(millis) -
                java.util.concurrent.TimeUnit.MINUTES.toSeconds(minutes);
        long milliseconds = millis - java.util.concurrent.TimeUnit.SECONDS.toMillis(seconds);
        return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);
    }
}
