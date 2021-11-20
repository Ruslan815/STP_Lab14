package my_number;

import java.util.Scanner;

// 1.82+i*-2.5
public class EditorComplexNumber {
    public static final String DELIMITER = ".";
    public static final String COMPLEX_DELIMITER = "+i*";
    public static final String ZERO = "0+i*0";
    public static final String REAL_MINUS_ZERO = "-0+i*0";
    public static final String IMG_MINUS_ZERO = "0+i*-0";
    public static final String BOTH_MINUS_ZERO = "-0+i*-0";

    private ComplexNumber currentNumber;
    private String currentNumberString;
    private boolean isImgPartExist;
    private boolean isRealFractional;
    private boolean isImgFractional;
    private int inputDigit;

    EditorComplexNumber(ComplexNumber number) {
        isImgPartExist = true;
        if (Math.abs(number.getReal() - (int) number.getReal()) > 0.00000001) {
            isRealFractional = true;
        }
        if (Math.abs(number.getImg() - (int) number.getImg()) > 0.00000001) {
            isImgFractional = true;
        }
        this.currentNumber = number;
        this.currentNumberString = number.toString();
    }

    public boolean isZero() {
        return currentNumberString.equals(EditorComplexNumber.ZERO)
                || currentNumberString.equals(EditorComplexNumber.REAL_MINUS_ZERO)
                || currentNumberString.equals(EditorComplexNumber.IMG_MINUS_ZERO)
                || currentNumberString.equals(EditorComplexNumber.BOTH_MINUS_ZERO);
    }

    public String addRealMinusSign() {
        if (currentNumberString.charAt(0) == '-') {
            currentNumberString = currentNumberString.substring(1);
        } else {
            currentNumberString = "-" + currentNumberString;
        }
        return currentNumberString;
    }

    public String addImgMinusSign() {
        if (isImgPartExist && currentNumberString.charAt(currentNumberString.length() - 1) != '*') {
            int indexOfImgPart = currentNumberString.indexOf('*') + 1;
            StringBuilder sb = new StringBuilder(currentNumberString);
            if (currentNumberString.charAt(indexOfImgPart) == '-') {
                sb.deleteCharAt(indexOfImgPart);
            } else {
                sb.insert(indexOfImgPart, '-');
            }
            currentNumberString = sb.toString();
        }
        return currentNumberString;
    }

    public String addDigit(int digit) {
        if (isImgPartExist && currentNumberString.charAt(currentNumberString.length() - 1) != '*') { // Img
            int indexOfImgPart = currentNumberString.indexOf('*') + 1;
            String imgPartStr = currentNumberString.substring(indexOfImgPart);
            StringBuilder sb = new StringBuilder(currentNumberString);
            if ((imgPartStr.equals("0") || imgPartStr.equals("-0")) && digit != 0) {
                sb.deleteCharAt(sb.lastIndexOf("0"));
            } else if (imgPartStr.equals("0") || imgPartStr.equals("-0")) { // Когда пытаются вписать в начало много нулей
                return currentNumberString;
            }
            sb.append((char) (digit + '0'));
            currentNumberString = sb.toString();
        } else if (currentNumberString.charAt(currentNumberString.length() - 1) == '*') { // Empty Img
            currentNumberString = currentNumberString + (char) (digit + '0');
        } else { // Real
            if ((currentNumberString.equals("0") || currentNumberString.equals("-0")) && digit != 0) {
                currentNumberString = currentNumberString.replace("0", "");
            } else if (currentNumberString.equals("0") || currentNumberString.equals("-0")) { // Когда пытаются вписать в начало много нулей
                return currentNumberString;
            }
            currentNumberString = currentNumberString + (char) (digit + '0');
        }
        return currentNumberString;
    }

    public String backspace() {
        if (isImgPartExist) { // Img
            if (currentNumberString.charAt(currentNumberString.length() - 1) == '*') {
                isImgPartExist = false;
                currentNumberString = currentNumberString.substring(0, currentNumberString.length() - 3);
                return currentNumberString;
            }

            int indexOfImgPart = currentNumberString.indexOf('*') + 1;
            String imgPartStr = currentNumberString.substring(indexOfImgPart);
            if ((imgPartStr.charAt(0) == '-' && imgPartStr.length() == 2) ||
                    imgPartStr.length() == 1) {
                currentNumberString = currentNumberString.substring(0, indexOfImgPart);
                return currentNumberString;
            }

            char lastSymbol = currentNumberString.charAt(currentNumberString.length() - 1);
            if (lastSymbol == '.') {
                isImgFractional = false;
            }
        } else { // Real
            if ((currentNumberString.charAt(0) == '-' && currentNumberString.length() == 2) ||
                    currentNumberString.length() == 1) {
                currentNumberString = "0";
                return currentNumberString;
            }

            char lastSymbol = currentNumberString.charAt(currentNumberString.length() - 1);
            if (lastSymbol == '.') {
                isRealFractional = false;
            }

        }

        currentNumberString = currentNumberString.substring(0, currentNumberString.length() - 1);
        return currentNumberString;
    }

    public String clear() {
        isImgPartExist = true;
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

    public ComplexNumber getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(ComplexNumber currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String edit(int command) {
        switch (command) {
            case 45: // make negative
                if (isImgPartExist) {
                    addImgMinusSign();
                } else {
                    addRealMinusSign();
                }
                break;
            case 46: // make fractional
                if (isImgPartExist) {
                    if (!isImgFractional && currentNumberString.indexOf("*") != currentNumberString.length() - 1) {
                        isImgFractional = true;
                        currentNumberString = currentNumberString + DELIMITER;
                    }
                } else {
                    if (!isRealFractional) {
                        isRealFractional = true;
                        currentNumberString = currentNumberString + DELIMITER;
                    }
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
            case 105: // add img delimiter
                if (!isImgPartExist) {
                    isImgPartExist = true;
                    currentNumberString = currentNumberString + COMPLEX_DELIMITER;
                }
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
                if (isImgPartExist) {
                    currentNumber = new ComplexNumber(currentNumberString);
                } else {
                    currentNumber = new ComplexNumber(Double.parseDouble(currentNumberString), 0);
                }

                return;
            }
            edit(inputString.charAt(0));
        }
    }
}
