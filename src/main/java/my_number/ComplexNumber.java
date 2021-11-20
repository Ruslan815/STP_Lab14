package my_number;

import java.util.Objects;

public class ComplexNumber extends MyNumber {
    private final double a; // Real part
    private final double b; // Imaginary part

    public ComplexNumber() {
        a = 0;
        b = 0;
    }

    public ComplexNumber(double real, double imaginary) {
        this.a = real;
        this.b = imaginary;
    }

    public ComplexNumber(String complexNumberString) {
        String[] numbers = complexNumberString.replace("i", "").replace("*", "").
                replace(" ", "").split("\\+");
        try {
            this.a = Double.parseDouble(numbers[0]);
            this.b = Double.parseDouble(numbers[1]);
        } catch (NullPointerException ex) {
            throw new ComplexNumberException("Укажите два числа a и b в формате: \"a + i*b\".");
        } catch (NumberFormatException ex) {
            throw new ComplexNumberException("Числа a и b должны быть вещественными.");
        }
    }

    public ComplexNumber copy() {
        return new ComplexNumber(this.a, this.b);
    }

    public ComplexNumber add(ComplexNumber number) {
        return new ComplexNumber(this.a + number.getReal(), this.b + number.getImg());
    }

    public ComplexNumber multiply(ComplexNumber number) {
        return new ComplexNumber(
                this.a * number.getReal() - this.b * number.getImg(),
                this.a * number.getImg() + number.getReal() * this.b);
    }

    public ComplexNumber square() {
        return this.multiply(this);
    }

    public ComplexNumber inverse() {
        return new ComplexNumber(
                this.a / (this.a * this.a + this.b * this.b),
                -this.b / (this.a * this.a + this.b * this.b));
    }

    public ComplexNumber subtract(ComplexNumber number) {
        return new ComplexNumber(this.a - number.getReal(), this.b - number.getImg());
    }

    public ComplexNumber divide(ComplexNumber number) {
        return new ComplexNumber(
                (this.a * number.getReal() + this.b * number.getImg()) / (number.getReal() * number.getReal() + number.getImg() * number.getImg()),
                (number.getReal() * this.b - this.a * number.getImg()) / (number.getReal() * number.getReal() + number.getImg() * number.getImg()));
    }

    public ComplexNumber negate() {
        return new ComplexNumber(-this.a, -this.b);
    }

    public double module() {
        return Math.sqrt(this.a * this.a + this.b * this.b);
    }

    public double angleRad() {
        if (this.a > 0) {
            return Math.atan(this.b / this.a);
        } else if (this.a == 0 && b > 0) {
            return Math.PI / 2;
        } else if (this.a < 0) {
            return Math.atan(this.b / this.a) + Math.PI;
        } else if (this.a == 0 && this.b < 0) {
            return -Math.PI / 2;
        }
        // if a == 0 && b == 0
        throw new ComplexNumberException("Значение аргумента fi не определено для нулевого комплексного числа: " + this);
    }

    public double angleDeg() {
        return this.angleRad() * 180 / Math.PI;
    }

    public ComplexNumber pow(int n) {
        if (n < 0) throw new ComplexNumberException("Показатель степени должен быть положительным числом.");
        return new ComplexNumber(
                Math.pow(this.module(), n) * Math.cos(n * this.angleRad()),
                Math.pow(this.module(), n) * Math.sin(n * this.angleRad()));
    }

    public ComplexNumber sqrt(int n, int i) {
        if (n < 0) throw new ComplexNumberException("Показатель степени корня должен быть положительным числом.");
        if (i < 0 || i > n - 1) throw new ComplexNumberException("i-ый корень числа должен быть в диапазоне 0<=i<n.");
        /*ComplexNumber[] answer = new ComplexNumber[n];
        for (int k = 0; k < n; k++) {
            answer[k] = new ComplexNumber(
                    this.module() * Math.cos((this.angleRad() + 2 * k * Math.PI) / n),
                    this.module() * Math.sin((this.angleRad() + 2 * k * Math.PI) / n))
                    .pow(1 / n);
        }

        return answer[i];*/
        return new ComplexNumber(
                Math.pow(this.module(), (double) 1 / n) * Math.cos((this.angleRad() + 2 * i * Math.PI) / n),
                Math.pow(this.module(), (double) 1 / n) * Math.sin((this.angleRad() + 2 * i * Math.PI) / n));
    }

    public boolean isEqual(ComplexNumber number) {
        return Math.abs(this.a - number.getReal()) < 0.00000001D && Math.abs(this.b - number.getImg()) < 0.00000001D;
    }

    public boolean isNotEqual(ComplexNumber number) {
        return !this.isEqual(number);
    }

    public double getReal() {
        return a;
    }

    public double getImg() {
        return b;
    }

    public String getRealStr() {
        return String.valueOf(a);
    }

    public String getImgStr() {
        return String.valueOf(b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Double.compare(that.a, a) == 0 && Double.compare(that.b, b) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return a + "+i*" + b;
    }
}
