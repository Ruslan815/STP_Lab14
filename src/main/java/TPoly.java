import java.util.LinkedList;
import java.util.List;

public class TPoly <T>{

    List<TMember<T>> polynomial;

    public TPoly(long coefficient, long degree) {
        polynomial = new LinkedList<>();
        polynomial.add(new TMember<>(coefficient, degree));
    }

    public TPoly(List<TMember<T>> somePolynomial) {
        polynomial = somePolynomial;
    }

    public void addElement(TMember<T> element) {
        polynomial.add(element);
    }

    public long getMaxDegreeOfPoly() {
        long maxDegree = Long.MIN_VALUE;
        for (TMember<T> member : polynomial) {
            if (member.getCoefficient() != 0 && member.getDegree() > maxDegree) {
                maxDegree = member.getDegree();
            }
        }
        if (maxDegree == Long.MIN_VALUE) maxDegree = 0;
        return maxDegree;
    }

    public long getCoefficient(long degree) {
        if (polynomial.isEmpty()) throw new TPolyException("Poly is empty");
        long coefficient = 0;
        for (TMember<T> member : polynomial) {
            if (member.getDegree() == degree) {
                coefficient = member.getCoefficient();
                break;
            }
        }
        return coefficient;
    }

    public void clear() {
        polynomial.clear();
    }

    public TPoly<T> add(TPoly<T> somePoly) {
        long maxDegree = Math.max(this.getMaxDegreeOfPoly(), somePoly.getMaxDegreeOfPoly());
        List<TMember<T>> newList = new LinkedList<>();
        for (int i = 0; i <= maxDegree; i++) {
            newList.add(new TMember<>(this.getCoefficient(i) + somePoly.getCoefficient(i), i));
        }
        return new TPoly<>(newList);
    }

    public TPoly<T> multiply(TPoly<T> somePoly) {
        long maxDegree = Math.max(this.getMaxDegreeOfPoly(), somePoly.getMaxDegreeOfPoly());
        List<TMember<T>> newList = new LinkedList<>();
        for (int i = 0; i <= maxDegree; i++) {
            newList.add(new TMember<>(this.getCoefficient(i) * somePoly.getCoefficient(i), i));
        }
        return new TPoly<>(newList);
    }

    public TPoly<T> diff(TPoly<T> somePoly) {
        long maxDegree = Math.max(this.getMaxDegreeOfPoly(), somePoly.getMaxDegreeOfPoly());
        List<TMember<T>> newList = new LinkedList<>();
        for (int i = 0; i <= maxDegree; i++) {
            newList.add(new TMember<>(this.getCoefficient(i) - somePoly.getCoefficient(i), i));
        }
        return new TPoly<>(newList);
    }

    public TPoly<T> minus() {
        List<TMember<T>> newList = new LinkedList<>();
        for (TMember<T> member : polynomial) {
            newList.add(new TMember<>(-member.getCoefficient(), member.getDegree()));
        }
        return new TPoly<>(newList);
    }

    public boolean isEquals(TPoly<T> somePoly) {
        long maxDegree = Math.max(this.getMaxDegreeOfPoly(), somePoly.getMaxDegreeOfPoly());
        for (int i = 0; i <= maxDegree; i++) {
            if (this.getCoefficient(i) != somePoly.getCoefficient(i)) return false;
        }
        return true;
    }

    public TPoly<T> derivative() {
        List<TMember<T>> newList = new LinkedList<>();
        for (TMember<T> member : polynomial) {
            newList.add(member.derivative());
        }
        return new TPoly<>(newList);
    }

    public double calculate(double x) {
        double answer = 0;
        for (TMember<T> member : polynomial) {
            answer += member.calculate(x);
        }
        return answer;
    }

    public TMember<T> getElementByIndex(int index) {
        return polynomial.get(index);
    }

    public List<TMember<T>> getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(List<TMember<T>> polynomial) {
        this.polynomial = polynomial;
    }
}
