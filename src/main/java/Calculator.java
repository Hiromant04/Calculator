
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Calculator {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите арифметическую операцию: ");
            String input = scanner.next();
            String result = calc(input);                        // вызываем новый созданный статический метод
            System.out.println(result);

        }
    }

    public static String calc(String input) {                   // создали статический метод, который вызываем из Main
        // объявим все нужные переменные
        List<String> arab = new ArrayList(Arrays.asList("10", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        List<String> rome = new ArrayList(Arrays.asList("X", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"));
        int result = 0;
        int left = 0;
        int right = 0;
        boolean isRome = false;

        try {
            List<String> operations = new ArrayList(Arrays.asList("+", "-", "/", "*"));
            int j = 0;
            char[] charArray = input.toCharArray();
            for (int i = 0; i < operations.size(); i++) {
                for (char item : charArray) {
                    if (String.valueOf(item).equals(operations.get(i))) {
                        j++;
                    }
                }
            }
            if (j > 1){
                throw new IllegalArgumentException("Может использоваться только один математический оператор");
            }
            // 1. проверим, что в строке есть оператор и запомним его, чтобы во 2м действии поделить строку на части
            // "до" оператора и "после" оператора
            String action = "";

            j = 0;
            for (int i = 0; i < operations.size(); i++) {
                if (input.contains(operations.get(i))) {
                    action = operations.get(i);                 // сохраняем оператор в переменную
                    break;
                }
                j++;
            }
            if (j == 4) {
                throw new IllegalArgumentException("В выражении не указано арифметическое действие");   // тут кидаем исключение.
            }

            //теперь поделим строку на части "до" оператора и "после" оператора
            String[] parts = input.split(Pattern.quote(action));               // в языках программирования нумерация идет с 0.
            // поэтому то, что "до" оператора, лежит с индексом 0
            // то, что "после" оператора, лежит с индексом 1
            // теперь надо проверить, что левая и правая части - числа, учитывая, что могут быть римские цифры
            // сначала проверим, арабское ли!?

            if ((!rome.contains(parts[0]) || !rome.contains(parts[1])) &&
                    (!arab.contains(parts[0]) || !arab.contains(parts[1])
                    )) {
                throw new IllegalArgumentException("Вне диапазона");
            }

            if (arab.contains(parts[0]) && arab.contains(parts[1])) {
                left = Integer.parseInt(parts[0]);               // Integer.parseInt() - метод самой джавы, который преобразует строку в число
                right = Integer.parseInt(parts[1]);

            }
            // вдруг римское!?
            if (rome.contains(parts[0]) && rome.contains(parts[1])) {
                left = Integer.parseInt(arab.get(rome.indexOf(parts[0])));        // тут мы вытаскиваем из массива arab элемент, индекс
                // которого равен индексу элемента в массиве
                // rome(элемент которого равен тому, что мы ввели в консоли)
                right = Integer.parseInt(arab.get(rome.indexOf(parts[1])));

                if (left <= right && action.equals("-")){
                    throw new IllegalArgumentException("Результат вычитания при использовании римских цифр не может " +
                            "быть отрицательным или равным нулю");   // тут кидаем исключение.
                }
            }
            // а если у нас часть римская, а часть арабская, то кидаем ошибку
            if (arab.contains(parts[0]) && rome.contains(parts[1]) ||
                    rome.contains(parts[0]) && arab.contains(parts[1])) {
                throw new IllegalArgumentException("Не должно быть действия над арабскими и римскими числами " +
                        "в одном действии");
            }

            // а теперь сами действия
            if (action.equals("+")) {
                result = left + right;
            }
            if (action.equals("-")) {
                result = left - right;
            }
            if (action.equals("*")) {
                result = left * right;
            }
            if (action.equals("/")) {
                if (right == 0) {
                    throw new IllegalArgumentException("На ноль делить нельзя");
                }
                result = left / right;
            }
            if (isRome){
                if (result <= 10){
                    return rome.get(arab.indexOf(String.valueOf(result)));
                }
                else {
                    int a = result - 10;
                    int b = 10;
                    return rome.get(arab.indexOf(String.valueOf(b))).concat(rome.get(arab.indexOf(String.valueOf(a))));
                }
            }
            return String.valueOf(result);                  // возвращаем результат, преобразовав его в строку
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}







