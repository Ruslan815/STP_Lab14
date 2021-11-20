package my_number;

public class SimpleFraction extends MyNumber implements Comparable<SimpleFraction>{
    public static final SimpleFraction zero = new SimpleFraction(0, 1);

    private long num;   // the numerator
    private long den;   // the denominator

    public SimpleFraction() {
        num = 0;
        den = 1;
    }

    // create and initialize a new SimpleFraction object
    public SimpleFraction(long numerator, long denominator) {
        if (denominator == 0) {
            throw new SimpleFractionException("denominator is zero");
        }

        // reduce fraction
        long g = gcd(numerator, denominator);
        num = numerator / g;
        den = denominator / g;

        // needed only for negative numbers
        if (den < 0) {
            den = -den;
            num = -num;
        }
    }

    // create and initialize a new SimpleFraction object
    public SimpleFraction(String someFraction) {
        someFraction = someFraction.replace(" ", "");
        String[] numeratorAndDenominator = someFraction.split("/");
        long numerator = Long.parseLong(numeratorAndDenominator[0]);
        long denominator = Long.parseLong(numeratorAndDenominator[1]);

        if (denominator == 0) {
            throw new SimpleFractionException("denominator is zero");
        }

        // reduce fraction
        long g = gcd(numerator, denominator);
        num = numerator / g;
        den = denominator / g;

        // needed only for negative numbers
        if (den < 0) {
            den = -den;
            num = -num;
        }
    }

    public SimpleFraction copy() {
        return new SimpleFraction(this.num, this.den);
    }

    // return a + b, staving off overflow
    public SimpleFraction add(SimpleFraction b) {
        SimpleFraction a = this;

        // special cases
        if (a.compareTo(zero) == 0) return b;
        if (b.compareTo(zero) == 0) return a;

        // Find gcd of numerators and denominators
        long f = gcd(a.num, b.num);
        long g = gcd(a.den, b.den);

        // add cross-product terms for numerator
        SimpleFraction s = new SimpleFraction((a.num / f) * (b.den / g) + (b.num / f) * (a.den / g),
                lcm(a.den, b.den));

        // multiply back in
        s.num *= f;
        return s;
    }

    // return a * b, staving off overflow as much as possible by cross-cancellation
    public SimpleFraction multiply(SimpleFraction b) {
        SimpleFraction a = this;

        // reduce p1/q2 and p2/q1, then multiply, where a = p1/q1 and b = p2/q2
        SimpleFraction c = new SimpleFraction(a.num, b.den);
        SimpleFraction d = new SimpleFraction(b.num, a.den);
        return new SimpleFraction(c.num * d.num, c.den * d.den);
    }

    // return a - b
    public SimpleFraction subtract(SimpleFraction b) {
        SimpleFraction a = this;
        return a.add(b.negate());
    }

    // return -a
    public SimpleFraction negate() {
        return new SimpleFraction(-num, den);
    }

    // return a / b
    public SimpleFraction divide(SimpleFraction b) {
        if (b.num == 0) {
            throw new SimpleFractionException("denominator is zero");
        }
        SimpleFraction a = this;
        return a.multiply(b.inverse());
    }

    // reversed fraction
    public SimpleFraction inverse() {
        return new SimpleFraction(den, num);
    }

    // return a^2
    public SimpleFraction square() {
        return this.multiply(this);
    }

    // checks equal of two simpleFractions
    public boolean isEquals(SimpleFraction b) {
        return (this.num == b.numerator() && this.den == b.denominator());
    }

    // checks that this simpleFractions is greater than passed simpleFractions
    public boolean isGreater(SimpleFraction b) {
        return this.compareTo(b) > 0;
    }

    // return the numerator of (this)
    public long numerator() {
        return num;
    }

    // return the denominator of (this)
    public long denominator() {
        return den;
    }

    // return the double numerator of (this)
    public double getDoubleNumerator() {
        return (double) this.num;
    }

    // return the double denominator of (this)
    public double getDoubleDenominator() {
        return (double) this.den;
    }

    // return the string numerator of (this)
    public String getStringNumerator() {
        return Long.toString(this.num);
    }

    // return the string denominator of (this)
    public String getStringDenominator() {
        return Long.toString(this.den);
    }

    // return string representation of (this)
    public String toString() {
        /*if (den == 1) return num + "";
        else return num + "/" + den;*/
        return num + "/" + den;
    }

    /***The next following utility methods***/

    // return double precision representation of (this)
    public double toDouble() {
        return (double) num / den;
    }

    // return { -1, 0, +1 } if a < b, a = b, or a > b
    public int compareTo(SimpleFraction b) {
        SimpleFraction a = this;
        long lhs = a.num * b.den;
        long rhs = a.den * b.num;
        if (lhs < rhs) return -1;
        if (lhs > rhs) return +1;
        return 0;
    }

    // is this SimpleFraction object equal to y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        SimpleFraction b = (SimpleFraction) y;
        return compareTo(b) == 0;
    }

    // hashCode consistent with equals() and compareTo()
    // (better to hash the numerator and denominator and combine)
    public int hashCode() {
        return this.toString().hashCode();
    }


    // create and return a new simpleFraction (r.num + s.num) / (r.den + s.den)
    public static SimpleFraction mediant(SimpleFraction r, SimpleFraction s) {
        return new SimpleFraction(r.num + s.num, r.den + s.den);
    }

    // return gcd(|m|, |n|)
    private static long gcd(long m, long n) {
        if (m < 0) m = -m;
        if (n < 0) n = -n;
        if (0 == n) return m;
        else return gcd(n, m % n);
    }

    // return lcm(|m|, |n|)
    private static long lcm(long m, long n) {
        if (m < 0) m = -m;
        if (n < 0) n = -n;
        return m * (n / gcd(m, n));    // parentheses important to avoid overflow
    }

    // return |a|
    public SimpleFraction abs() {
        if (num >= 0) return this;
        else return negate();
    }

    /*public static void main(String[] args) {
        SimpleFraction x, y, z;

        // 1/2 + 1/3 = 5/6
        x = new SimpleFraction(1, 2);
        y = new SimpleFraction(1, 3);
        z = x.plus(y);
        System.out.println(z);

        // 8/9 + 1/9 = 1
        x = new SimpleFraction(8, 9);
        y = new SimpleFraction(1, 9);
        z = x.plus(y);
        System.out.println(z);

        // 1/200000000 + 1/300000000 = 1/120000000
        x = new SimpleFraction(1, 200000000);
        y = new SimpleFraction(1, 300000000);
        z = x.plus(y);
        System.out.println(z);

        // 1073741789/20 + 1073741789/30 = 1073741789/12
        x = new SimpleFraction(1073741789, 20);
        y = new SimpleFraction(1073741789, 30);
        z = x.plus(y);
        System.out.println(z);

        //  4/17 * 17/4 = 1
        x = new SimpleFraction(4, 17);
        y = new SimpleFraction(17, 4);
        z = x.times(y);
        System.out.println(z);

        // 3037141/3247033 * 3037547/3246599 = 841/961
        x = new SimpleFraction(3037141, 3247033);
        y = new SimpleFraction(3037547, 3246599);
        z = x.times(y);
        System.out.println(z);

        // 1/6 - -4/-8 = -1/3
        x = new SimpleFraction(1, 6);
        y = new SimpleFraction(-4, -8);
        z = x.minus(y);
        System.out.println(z);
    }*/

}
