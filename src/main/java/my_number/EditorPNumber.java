package my_number;

import java.util.Scanner;

public class EditorPNumber {
    public static final String DELIMITER = ".";
    public static final String ZERO = "0";
    public static final String MINUS_ZERO = "-0";

    private PNumber currentNumber;
    private String currentNumberString;
    private boolean isFractional;
    private int inputDigit;

    EditorPNumber(PNumber number) {
        this.currentNumber = number;

        if (Math.abs(number.getNumber()) - (int) Math.abs(number.getNumber()) > 0.000000001) {
            isFractional = true;
            currentNumberString = number.getNumberString();
        } else {
            currentNumberString = String.valueOf((int) number.getNumber());
            isFractional = false;
        }
    }

    public boolean isZero() {
        return currentNumberString.equals(EditorPNumber.ZERO)
                || currentNumberString.equals(EditorPNumber.MINUS_ZERO);
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
        if (digit < 0 || digit >= currentNumber.getBase()) {
            return currentNumberString;
        }
        if (isFractional && countOfFractionSymbols() == currentNumber.getAccuracy()) {
            return currentNumberString;
        }

        if (isZero()) {
            currentNumberString = currentNumberString.replace("0", "");
        }

        if (digit < 10) {
            currentNumberString = currentNumberString + (char) (digit + '0');
        } else {
            currentNumberString = currentNumberString + ((char) (digit - 10 + 65)); // 'A' = 65
        }

        return currentNumberString;
    }

    private int countOfFractionSymbols() {
        int indexOfDot = currentNumberString.indexOf(DELIMITER);
        return currentNumberString.length() - indexOfDot - 1;
    }

    public String backspace() {
        char lastSymbol = currentNumberString.charAt(currentNumberString.length() - 1);
        if (lastSymbol == '.') {
            isFractional = false;
            currentNumberString = currentNumberString.replace(".", "");
            return currentNumberString;
        }

        if ((currentNumberString.charAt(0) == '-' && currentNumberString.length() == 2)
                || currentNumberString.length() == 1) {
            currentNumberString = ZERO;
            return currentNumberString;
        }

        currentNumberString = currentNumberString.substring(0, currentNumberString.length() - 1);
        return currentNumberString;
    }

    public String clear() {
        isFractional = false;
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

    public PNumber getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(PNumber currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String edit(int command) {
        switch (command) {
            case 45: // make negative
                addMinusSign();
                break;
            case 46: // make fractional
                if (!isFractional) {
                    isFractional = true;
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
            case 65: // A
            case 66: // B
            case 67: // C
            case 68: // D
            case 69: // E
            case 70: // F
                if (command <= 57) {
                    inputDigit = command - '0';
                } else {
                    inputDigit = command - 'A' + 10;
                }
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
                currentNumber = new PNumber(currentNumberString, currentNumber.getBaseString(), currentNumber.getAccuracyString());
                return;
            }
            edit(inputString.charAt(0));
        }
    }
}
