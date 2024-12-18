import java.io.PrintStream;
import java.util.Comparator;
import java.util.Scanner;
import java.io.IOException;

import static java.util.Arrays.sort;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static PrintStream out = System.out;
    public static void main(String[] args)  throws IOException {
        //Считываем N
        int N = in.nextInt();
        //Создаем двумерный массив необходимого размера
        char[][] mainArray = new char[N][N];
        // Наполняем массив символами
        for (int i = 0; i < mainArray.length; i++) {
            for (int j = 0; j < mainArray[i].length; j++)
                mainArray[i][j] = (char) System.in.read();
        }
        // Сортируем столбцы массива по заданным условиям
        // Создаем массив который будет хранить данные о:
        // 1) кол-ве гласных в столбце , 2) сумме ASCII- кодов
        // 3) изначальное положение столбца в массиве
        int[][] countVowels = new int[N][3];
        // Наполняем этот массив
        for (int i = 0; i < N; i++) {
            // запоминаем начальное положение столбца в массиве
            countVowels[i][2] = i;
            for (int j = 0; j < N; j++) {
                // в 1 ячейке подсчитываем кол-во гласных
                switch (mainArray[j][i]) {
                    case 'a':
                    case 'e':
                    case 'i':
                    case 'o':
                    case 'u':
                    case 'A':
                    case 'E':
                    case 'I':
                    case 'O':
                    case 'U':
                        countVowels[i][0] += 1;
                }
                // во 2 сумму ASCII-кодов
                countVowels[i][1] += (int)mainArray[j][i];
            }
        }
        // сортируем массив
        sort(countVowels, Comparator.comparingInt((int[] arr) -> arr[0])
                .thenComparing(arr -> arr[1]));
        // создаем новый отсортированный массив
        char[][] newMainArray = new char[N][N];
        for (int i = 0; i < N; i++) {
            int k = countVowels[i][2];
            for (int j = 0; j < N; j++) {
                newMainArray[j][i] = mainArray[j][k];
            }
        }
        // ищем символы которые встречаются чаще всего
        out.print("Элемент(ы) встречающиеся чаще всего - ");
        // создаем массив, хранящий данные о кол-ве каждой буквы английского алфавита
        int famousSimbolArray [] = new int[26];
        // наполняем его данными
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (newMainArray[i][j] <= 'z' && newMainArray[i][j] >= 'a')
                    famousSimbolArray[(int)newMainArray[i][j] - 97]++;
                else
                    famousSimbolArray[(int)newMainArray[i][j] - 65]++;
            }
        }
        // ищем максимум массива
        int maxCount = 0;
        for (int i = 0; i < 26; i++)
            if (maxCount < famousSimbolArray[i]) maxCount = famousSimbolArray[i];
        // выводим все символы встречающиеся максю кол-во раз
        for (int i = 0; i < 26; i++)
            if (maxCount == famousSimbolArray[i]) out.print((char)(i + 97) + " ");
        out.print("\n");

        //вывод массива по спирали, начиная с центрального элемента
        out.print("Массив по спирали - ");
        //если N четное приниаем за центральный элемент, элемент с координатами [N/2][N/2]
        int step = 0; // количетво шагов по спирали
        // коррдинаты центрального элемента
        int lineCount = (int)(Math.ceil((double)N/2) - 1), colummCount = lineCount;
        out.print(newMainArray[lineCount][colummCount] + " "); // вывод центрального элемента
        while (step != N-1) { // кол-во шагов не может быть больше кол-ва чисел в строке/столбце
            step++; // увеличиваем кол-во шагов
            for (int i = 0; i < step; i++) { // идем по спирали вниз
                lineCount++;
                out.print(newMainArray[lineCount][colummCount] + " ");
            }
            for (int i = 0; i < step; i++) { // по спирали вправо
                colummCount++;
                out.print(newMainArray[lineCount][colummCount] + " ");
            }
            if (step == N-1) break; // проверяем для N-четно, делаем последние по круга по спирали
            step++;
            for (int i = 0; i < step; i++) { // по спирали вверх
                lineCount--;
                out.print(newMainArray[lineCount][colummCount] + " ");
            }
            for (int i = 0; i < step; i++) { // по спирали влево
                colummCount--;
                out.print(newMainArray[lineCount][colummCount] + " ");
            }
        }
        // завершаем круг по спирали для N-четно вверх, N-нечетно вниз
        if (N%2 == 0) {
            for (int i = 0; i < step; i++) {
                lineCount--;
                out.print(newMainArray[lineCount][colummCount] + " ");
            }
        }
        else {
            for (int i = 0; i < step; i++) {
                lineCount++;
                out.print(newMainArray[lineCount][colummCount] + " ");
            }
        }
        out.print("\n");
        // шифруем массив, меняя регистр
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (newMainArray[i][j] <= 'z' && newMainArray[i][j] >= 'a')
                    newMainArray[i][j] = (char)((int)newMainArray[i][j] - 32);
                else
                    newMainArray[i][j] = (char)((int)newMainArray[i][j] + 32);
            }
        }
        //вывод отсортированного массива
        out.print("Зашифрованный массив - ");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                out.print(newMainArray[i][j] + " ");
        }
    }
}