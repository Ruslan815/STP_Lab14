package my_number;

import java.util.Scanner;

public class EditorSimpleFraction {
    public static final String DELIMITER = "/";
    public static final String ZERO = "0/1";
    public static final String MINUS_ZERO = "-0/1";

    private SimpleFraction currentNumber;
    private String currentNumberString;
    private boolean isDenominatorExist;
    private int inputDigit;

    EditorSimpleFraction(SimpleFraction number) {
        this.currentNumber = number;
        isDenominatorExist = number.getDoubleDenominator() != 1;
        currentNumberString = number.toString();
    }

    public boolean isZero() {
        return currentNumberString.equals(EditorSimpleFraction.ZERO)
                || currentNumberString.equals(EditorSimpleFraction.MINUS_ZERO);
    }

    public String addMinusSign() {
        if (currentNumberString.charAt(0) == '-') {
            currentNumberString = currentNumberString.substring(1);
        } else {
            currentNumberString = "-" + currentNumberString;
        }
        return currentNumberString;
    }

    public String addDigit(int digit) {
        if (isZero() && digit != 0) {
            currentNumberString = currentNumberString.replace(ZERO, "");
        } else if (isZero()) { // Когда пытаются вписать в начало много нулей
            return currentNumberString;
        }
        if (isDenominatorExist && (currentNumberString.length() - currentNumberString.indexOf('/') == 1) && digit == 0) { // Если вводят ноль в знаменатель
            return currentNumberString;
        }
        currentNumberString = currentNumberString + (char) (digit + '0');
        return currentNumberString;
    }

    public String backspace() {
        char lastSymbol = currentNumberString.charAt(currentNumberString.length() - 1);
        if (lastSymbol == '/') {
            isDenominatorExist = false;
            currentNumberString = currentNumberString.replace("/", "");
            return currentNumberString;
        }

        if ((currentNumberString.charAt(0) == '-' && currentNumberString.length() == 2) ||
                currentNumberString.length() == 1) {
            currentNumberString = ZERO;
            return currentNumberString;
        }

        currentNumberString = currentNumberString.substring(0, currentNumberString.length() - 1);
        return currentNumberString;
    }

    public String clear() {
        isDenominatorExist = false;
        currentNumberString = ZERO;
        return currentNumberString;
    }

    public String getCurrentNumberString() {
        return currentNumberString;
    }

    public void setCurrentNumberString(String currentNumberString) {
        this.currentNumberString = currentNumberString;
    }

    public int getInputDigit() {
        return this.inputDigit;
    }

    public void setInputDigit(int someDigit) {
        this.inputDigit = someDigit;
    }

    public SimpleFraction getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(SimpleFraction currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String edit(int command) {
        switch (command) {
            case 45: // make negative
                addMinusSign();
                break;
            case 47: // make fractional
                if (!isDenominatorExist) {
                    isDenominatorExist = true;
                    currentNumberString = currentNumberString + DELIMITER;
                }
                break;
            case 48: // 0
            case 49: // 1
            case 50: // 2
            case 51: // 3
            case 52: // 4
            case 53: // 5
            case 54: // 6
            case 55: // 7
            case 56: // 8
            case 57: // 9
                inputDigit = command - '0';
                addDigit(inputDigit);
                break;
            case 99: // clear
                clear();
                break;
            case 118: // delete symbol
                backspace();
                break;
        }

        return currentNumberString;
    }

    public void run() {
        while (true) {
            System.out.println("Команды:\n- Стереть(v);\n- Очистить экран(с)\n- Выйти(x)" +
                    "\nТекущее число: " + currentNumberString + "\n");
            Scanner in = new Scanner(System.in);
            String inputString = in.next();
            char command = inputString.charAt(0);

            if (command == 'x') {
                System.out.println("Завершение работы редактора.");
                if (isDenominatorExist) {
                    currentNumber = new SimpleFraction(currentNumberString);
                } else {
                    currentNumber = new SimpleFraction(Long.parseLong(currentNumberString), 1);
                }

                return;
            }
            edit(inputString.charAt(0));
        }
    }
}
