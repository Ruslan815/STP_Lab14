import org.junit.Assert;
import org.junit.Test;

public class TMemberTest {

    @Test
    public void createTMemberTest() {
        TMember member = new TMember(2, 5);

        Assert.assertEquals("2x^5", member.toString());
    }

    @Test
    public void isEquals() {
        TMember member1 = new TMember(2, 7);
        TMember member2 = new TMember(2, 7);

        Assert.assertTrue(member1.isEquals(member2));
    }

    @Test
    public void isNotEquals() {
        TMember member1 = new TMember(2, 5);
        TMember member2 = new TMember(3, 10);

        Assert.assertFalse(member1.isEquals(member2));
    }

    @Test
    public void derivative() {
        TMember member = new TMember(3, 10);

        member = member.derivative();

        Assert.assertEquals("30x^9", member.toString());
    }

    @Test
    public void calculate() {
        TMember member = new TMember(5, 2);

        double actual = member.calculate(5);

        Assert.assertEquals(125, actual, 0.000001);
    }

    @Test
    public void testToString() {
        TMember member = new TMember(25, 7);

        Assert.assertEquals("25x^7", member.toString());
    }
}