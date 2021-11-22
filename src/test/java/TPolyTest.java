import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TPolyTest {

    @Test
    public void createTPolyTest() {
        TPoly poly = new TPoly(1, 2);

        Assert.assertEquals(1, poly.getPolynomial().size());
    }

    @Test
    public void getMaxDegreeOfPoly() {
        TPoly poly = new TPoly(1, 2);
        poly.addElement(new TMember(5, 4));

        Assert.assertEquals(4, poly.getMaxDegreeOfPoly());
    }

    @Test
    public void getZeroMaxDegreeOfPoly() {
        TPoly poly = new TPoly(1, 0);

        Assert.assertEquals(0, poly.getMaxDegreeOfPoly());
    }

    @Test
    public void getCoefficient() {
        TPoly poly = new TPoly(1, 2);
        poly.addElement(new TMember(5, 4));

        Assert.assertEquals(5, poly.getCoefficient(4));
    }

    @Test
    public void getZeroCoefficient() {
        TPoly poly = new TPoly(1, 2);
        poly.addElement(new TMember(5, 4));

        Assert.assertEquals(0, poly.getCoefficient(1));
    }

    @Test
    public void clear() {
        TPoly poly = new TPoly(1, 2);

        poly.clear();

        Assert.assertTrue(poly.getPolynomial().isEmpty());
    }

    @Test
    public void add() {
        TPoly poly1 = new TPoly(1, 1);
        poly1.addElement(new TMember(4, 4));

        TPoly poly2 = new TPoly(2, 2);
        poly2.addElement(new TMember(3, 3));

        TPoly poly3 = poly1.add(poly2);
        List<TMember> list = poly3.getPolynomial();

        Assert.assertEquals("[1x^1, 2x^2, 3x^3, 4x^4]", list.toString());
    }

    @Test
    public void multiply() {
        TPoly poly1 = new TPoly(1, 1);
        poly1.addElement(new TMember(4, 4));

        TPoly poly2 = new TPoly(2, 1);
        poly2.addElement(new TMember(3, 4));

        TPoly poly3 = poly1.multiply(poly2);
        List<TMember> list = poly3.getPolynomial();

        Assert.assertEquals("[2x^1, 12x^4]", list.toString());
    }

    @Test
    public void diff() {
        TPoly poly1 = new TPoly(1, 1);
        poly1.addElement(new TMember(4, 4));

        TPoly poly2 = new TPoly(2, 1);
        poly2.addElement(new TMember(3, 4));

        TPoly poly3 = poly1.diff(poly2);
        List<TMember> list = poly3.getPolynomial();

        Assert.assertEquals("[-1x^1, 1x^4]", list.toString());
    }

    @Test
    public void minus() {
        TPoly poly1 = new TPoly(1, 1);
        poly1.addElement(new TMember(4, 2));

        TPoly poly2 = poly1.minus();
        List<TMember> list = poly2.getPolynomial();

        Assert.assertEquals("[-1x^1, -4x^2]", list.toString());
    }

    @Test
    public void isEquals() {
        TPoly poly1 = new TPoly(1, 1);
        poly1.addElement(new TMember(4, 4));

        TPoly poly2 = new TPoly(1, 1);
        poly2.addElement(new TMember(4, 4));

        Assert.assertTrue(poly1.isEquals(poly2));
    }

    @Test
    public void isNotEquals() {
        TPoly poly1 = new TPoly(1, 1);
        poly1.addElement(new TMember(4, 4));

        TPoly poly2 = new TPoly(2, 2);
        poly2.addElement(new TMember(3, 3));

        Assert.assertFalse(poly1.isEquals(poly2));
    }

    @Test
    public void derivative() {
        TPoly poly1 = new TPoly(1, 1);
        poly1.addElement(new TMember(4, 4));

        TPoly poly2 = poly1.derivative();

        Assert.assertEquals("[1, 16x^3]", poly2.getPolynomial().toString());
    }

    @Test
    public void calculate() {
        TPoly poly1 = new TPoly(1, 1);
        poly1.addElement(new TMember(4, 4));

        double actual = poly1.calculate(5);

        Assert.assertEquals(2505.0, actual, 0.0000001);
    }

    @Test
    public void getElementByIndex() {
        TPoly poly1 = new TPoly(1, 1);
        poly1.addElement(new TMember(4, 4));

        TMember member = poly1.getElementByIndex(1);

        Assert.assertEquals(new TMember(4, 4), member);
    }
}