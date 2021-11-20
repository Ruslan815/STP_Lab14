package my_number;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PNumber extends MyNumber {
    private final double number;
    private int base;
    private int accuracy;

    public PNumber() {
        number = 0;
        base = 10;
        accuracy = 3;
    }

    public PNumber(double n, int b, int a) {
        if (b < 2 || b > 16)
            throw new PNumberException("Основание P-ичного числа должно быть в диапазоне [2..16].");
        if (a < 0) throw new PNumberException("Точность P-ичного числа должна быть >= 0.");

        number = n;
        base = b;
        accuracy = a;
    }

    public PNumber(String n, String b, String a) {
        if (Integer.parseInt(b) < 2 || Integer.parseInt(b) > 16)
            throw new PNumberException("Основание P-ичного числа должно быть в диапазоне [2..16].");
        if (Integer.parseInt(a) < 0) throw new PNumberException("Точность P-ичного числа должна быть >= 0.");

        base = Integer.parseInt(b);
        number = convertNumberToTenBase(n, base);
        accuracy = Integer.parseInt(a);
    }

    public PNumber copy() {
        return new PNumber(number, base, accuracy);
    }

    public PNumber add(PNumber someNumber) {
        return new PNumber(number + someNumber.getNumber(), base, accuracy);
    }

    public PNumber multiply(PNumber someNumber) {
        return new PNumber(number * someNumber.getNumber(), base, accuracy);
    }

    public PNumber subtract(PNumber someNumber) {
        return new PNumber(number - someNumber.getNumber(), base, accuracy);
    }

    public PNumber divide(PNumber someNumber) {
        if (someNumber.getNumber() == 0) throw new PNumberException("Нельзя делить P-ичное число на ноль.");
        return new PNumber(number / someNumber.getNumber(), base, accuracy);
    }

    public PNumber inverse() {
        if (number == 0) throw new PNumberException("Нельзя делить P-ичное число на ноль.");
        return new PNumber(1 / number, base, accuracy);
    }

    public PNumber square() {
        return new PNumber(number * number, base, accuracy);
    }

    private double convertNumberToTenBase(String someNumber, int someBase) {
        double convertedNumber = 0;
        boolean isNegative = false;
        int currentDigit;
        int currentDigitIndex = 0;
        if (someNumber.charAt(0) == '-') {
            someNumber = someNumber.substring(1);
            isNegative = true;
        }

        while (currentDigitIndex < someNumber.length()) { // !=
            if (someNumber.charAt(currentDigitIndex) == ',' || someNumber.charAt(currentDigitIndex) == '.') break;
            currentDigitIndex++;
        }
        currentDigitIndex--;

        for (int i = 0; i < someNumber.length(); i++) {
            switch (someNumber.charAt(i)) {
                case 'a':
                case 'A':
                    currentDigit = 10;
                    break;
                case 'b':
                case 'B':
                    currentDigit = 11;
                    break;
                case 'c':
                case 'C':
                    currentDigit = 12;
                    break;
                case 'd':
                case 'D':
                    currentDigit = 13;
                    break;
                case 'e':
                case 'E':
                    currentDigit = 14;
                    break;
                case 'f':
                case 'F':
                    currentDigit = 15;
                    break;
                case '.':
                case ',':
                    currentDigit = 0;
                    currentDigitIndex = 0;
                    break;
                default: // Для цифр
                    currentDigit = someNumber.charAt(i) - '0';
            }

            if (currentDigit < 0) {
                throw new PNumberException("Некорректное значение P-ичного числа. " +
                        "Недопустимый символ: " + currentDigit + ".");
            }

            if (currentDigit >= someBase) {
                throw new PNumberException("Некорректное значение P-ичного числа. " +
                        "Недопустимый символ: " + currentDigit + ", при основании равном: " + someBase + ".");
            }

            convertedNumber += Math.pow(someBase, currentDigitIndex) * currentDigit;
            currentDigitIndex--;
        }

        return isNegative ? -convertedNumber : convertedNumber;
    }

    private String convertFromTenToCustomBase() {
        StringBuilder convertedNumber = new StringBuilder();
        List<Integer> integerPartList = new ArrayList<>();
        int integerPart = Math.abs((int) (number));
        while (integerPart != 0) {
            integerPartList.add(integerPart % base);
            integerPart /= base;
        }
        Collections.reverse(integerPartList);
        for (int currentDigit : integerPartList) convertedNumber.append(convertNumberToValidBase(currentDigit));
        if (accuracy == 0) return convertedNumber.toString();

        convertedNumber.append(".");
        double fractionPart = Math.abs(number) - Math.abs((int) (number));
        for (int i = 0; i < accuracy; i++) {
            fractionPart = (fractionPart * base);
            convertedNumber.append(convertNumberToValidBase((int) fractionPart));
            fractionPart -= (int) fractionPart;
        }

        if (number < 0) convertedNumber.insert(0, "-");
        return convertedNumber.toString();
    }

    private String convertNumberToValidBase(int someNumber) {
        if (someNumber < 10) return String.valueOf(someNumber);
        return String.valueOf((char) ((int) 'A' + (someNumber - 10)));
    }

    public double getNumber() {
        return number;
    }

    public String getNumberString() {
        return convertFromTenToCustomBase();
    }

    public int getBase() {
        return base;
    }

    public String getBaseString() {
        return String.valueOf(base);
    }

    public int getAccuracy() {
        return accuracy;
    }

    public String getAccuracyString() {
        return String.valueOf(accuracy);
    }

    public void setBase(int newBase) {
        if (newBase < 2 || newBase > 16)
            throw new PNumberException("Основание P-ичного числа должно быть в диапазоне [2..16].");
        base = newBase;
    }

    public void setBaseString(String newBase) {
        if (Integer.parseInt(newBase) < 2 || Integer.parseInt(newBase) > 16)
            throw new PNumberException("Основание P-ичного числа должно быть в диапазоне [2..16].");
        base = Integer.parseInt(newBase);
    }

    public void setAccuracy(int newAccuracy) {
        if (newAccuracy < 0) throw new PNumberException("Точность P-ичного числа должна быть >= 0.");
        accuracy = newAccuracy;
    }

    public void setAccuracyString(String newAccuracy) {
        if (Integer.parseInt(newAccuracy) < 0) throw new PNumberException("Точность P-ичного числа должна быть >= 0.");
        accuracy = Integer.parseInt(newAccuracy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PNumber pNumber = (PNumber) o;
        return Double.compare(pNumber.number, number) == 0 && base == pNumber.base && accuracy == pNumber.accuracy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, base, accuracy);
    }

    @Override
    public String toString() {
        return "PNumber{" +
                "number=" + number +
                ", base=" + base +
                ", accuracy=" + accuracy +
                '}';
    }
}
