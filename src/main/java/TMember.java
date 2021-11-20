import java.util.Objects;

public class TMember <T>{

    private long coefficient;
    private long degree;

    public TMember(long coefficient, long degree) {
        if (degree < 0) throw new TMemberException("Degree of polynomial member must be (_ > 0).");
        if (coefficient == 0) degree = 0; // Zero-polynomial
        this.coefficient = coefficient;
        this.degree = degree;
    }

    public boolean isEquals(TMember<T> member) {
        return this.equals(member);
    }

    public TMember<T> derivative() {
        long newCoefficient = this.coefficient * this.degree;
        long newDegree = this.degree - 1;
        return new TMember<>(newCoefficient, newDegree);
    }

    public double calculate(double number) {
        return Math.pow(number, degree) * coefficient;
    }

    public long getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(long coefficient) {
        this.coefficient = coefficient;
    }

    public long getDegree() {
        return degree;
    }

    public void setDegree(long degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        if (coefficient == 0) return String.valueOf(0);
        if (degree == 0) return String.valueOf(coefficient);
        return coefficient + "x^" + degree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TMember<?> tMember = (TMember<?>) o;
        return coefficient == tMember.coefficient && degree == tMember.degree;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficient, degree);
    }
}
