import java.util.LinkedList;
import java.util.List;

public class TPoly {

    List<TMember> polynomial;

    public TPoly(long coefficient, long degree) {
        polynomial = new LinkedList<>();
        polynomial.add(new TMember(coefficient, degree));
    }

    public TPoly(List<TMember> somePolynomial) {
        polynomial = somePolynomial;
        normalize();
    }

    public void addElement(TMember element) {
        polynomial.add(element);
        normalize();
    }

    public long getMaxDegreeOfPoly() {
        if (polynomial.isEmpty()) throw new TPolyException("Poly is empty");
        long maxDegree = Long.MIN_VALUE;
        for (TMember member : polynomial) {
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
        for (TMember member : polynomial) {
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

    public TPoly add(TPoly somePoly) {
        long maxDegree = Math.max(this.getMaxDegreeOfPoly(), somePoly.getMaxDegreeOfPoly());
        List<TMember> newList = new LinkedList<>();
        for (int i = 0; i <= maxDegree; i++) {
            newList.add(new TMember(this.getCoefficient(i) + somePoly.getCoefficient(i), i));
        }
        return new TPoly(newList);
    }

    // TODO Умножение скобки на скобку
    public TPoly multiply(TPoly somePoly) {
        long maxDegree = Math.max(this.getMaxDegreeOfPoly(), somePoly.getMaxDegreeOfPoly());
        List<TMember> newList = new LinkedList<>();
        for (int i = 0; i <= maxDegree; i++) {
            newList.add(new TMember(this.getCoefficient(i) * somePoly.getCoefficient(i), i));
        }
        return new TPoly(newList);
    }

    public TPoly diff(TPoly somePoly) {
        long maxDegree = Math.max(this.getMaxDegreeOfPoly(), somePoly.getMaxDegreeOfPoly());
        List<TMember> newList = new LinkedList<>();
        for (int i = 0; i <= maxDegree; i++) {
            newList.add(new TMember(this.getCoefficient(i) - somePoly.getCoefficient(i), i));
        }
        return new TPoly(newList);
    }

    public TPoly minus() {
        List<TMember> newList = new LinkedList<>();
        for (TMember member : polynomial) {
            newList.add(new TMember(-member.getCoefficient(), member.getDegree()));
        }
        return new TPoly(newList);
    }

    public boolean isEquals(TPoly somePoly) {
        long maxDegree = Math.max(this.getMaxDegreeOfPoly(), somePoly.getMaxDegreeOfPoly());
        for (int i = 0; i <= maxDegree; i++) {
            if (this.getCoefficient(i) != somePoly.getCoefficient(i)) return false;
        }
        return true;
    }

    public TPoly derivative() {
        List<TMember> newList = new LinkedList<>();
        for (TMember member : polynomial) {
            newList.add(member.derivative());
        }
        return new TPoly(newList);
    }

    public double calculate(double x) {
        double answer = 0;
        for (TMember member : polynomial) {
            answer += member.calculate(x);
        }
        return answer;
    }

    public TMember getElementByIndex(int index) {
        return polynomial.get(index);
    }

    public List<TMember> getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(List<TMember> polynomial) {
        this.polynomial = polynomial;
    }

    public void normalize() {
        List<TMember> newList = new LinkedList<>();
        // Привести подобные и упорядочить
        long maxDegree = getMaxDegreeOfPoly();
        for (int i = 0; i <= maxDegree; i++) {
            long tempCoefficient = 0;
            for (TMember tempMember : polynomial) {
                if (tempMember.getDegree() == i) {
                    tempCoefficient += tempMember.getCoefficient();
                }
            }
            newList.add(new TMember(tempCoefficient, i));
        }
        polynomial = List.copyOf(newList);
        newList.clear();

        // Delete Zero-Poly
        for (TMember tMember : polynomial) {
            if (tMember.getCoefficient() != 0) {
                newList.add(tMember);
            }
        }
        polynomial = newList;
    }
}
