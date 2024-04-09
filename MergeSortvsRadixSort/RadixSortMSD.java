import java.util.*;

public class RadixSortMSD {
    private static final int RADIX = 10; // Base decimal

    public static void sort(int[] array) {
        // Separar os números negativos dos positivos
        List<Integer> negatives = new ArrayList<>();
        List<Integer> nonNegatives = new ArrayList<>();

        for (int item : array) {
            if (item < 0) {
                // Armazenar o valor absoluto dos negativos para ordenação
                negatives.add(-item);
            } else {
                nonNegatives.add(item);
            }
        }

        // Converter listas em arrays para o Radix Sort
        int[] negativesArray = negatives.stream().mapToInt(i -> i).toArray();
        int[] nonNegativesArray = nonNegatives.stream().mapToInt(i -> i).toArray();

        // Aplicar Radix Sort separadamente
        radixSort(negativesArray);
        radixSort(nonNegativesArray);

        // Inverter a ordem dos negativos para a ordenação correta
        for (int i = 0; i < negativesArray.length / 2; i++) {
            int temp = negativesArray[i];
            negativesArray[i] = -negativesArray[negativesArray.length - 1 - i];
            negativesArray[negativesArray.length - 1 - i] = -temp;
        }

        // Juntar os arrays novamente
        int index = 0;
        for (int value : negativesArray) {
            array[index++] = -value; // Reverter para valores negativos originais
        }
        for (int value : nonNegativesArray) {
            array[index++] = value;
        }
    }

    private static void radixSort(int[] array) {
        int max = Arrays.stream(array).max().orElse(0);
        for (int exp = 1; max / exp > 0; exp *= RADIX) {
            countSort(array, exp);
        }
    }

    private static void countSort(int[] array, int exp) {
        int[] output = new int[array.length];
        int[] count = new int[RADIX];
        Arrays.fill(count, 0);

        for (int value : array) {
            count[(value / exp) % RADIX]++;
        }

        for (int i = 1; i < RADIX; i++) {
            count[i] += count[i - 1];
        }

        for (int i = array.length - 1; i >= 0; i--) {
            output[count[(array[i] / exp) % RADIX] - 1] = array[i];
            count[(array[i] / exp) % RADIX]--;
        }

        System.arraycopy(output, 0, array, 0, array.length);
    }
}
