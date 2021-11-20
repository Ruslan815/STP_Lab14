import org.junit.Assert;
import org.junit.Test;
import my_number.*;

public class TMemoryTest {

    @Test
    public void constructorTest() {
        SimpleFraction expected = new SimpleFraction(0, 1);

        TMemory<SimpleFraction> memory = new TMemory<>(new SimpleFraction(0, 1));
        Assert.assertEquals("_Off", memory.getFState());
        SimpleFraction actual = memory.getFNumber();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setFNumberTest() {
        SimpleFraction expected = new SimpleFraction(4, 5);

        TMemory<SimpleFraction> memory = new TMemory<>(new SimpleFraction(0, 1));
        memory.setFNumber(expected);
        SimpleFraction actual = memory.getFNumber();

        Assert.assertEquals(expected, actual);
        Assert.assertEquals("_On", memory.getFState());
    }

    @Test
    public void addTest() {
        SimpleFraction expected = new SimpleFraction(3, 4);

        TMemory<SimpleFraction> memory = new TMemory<>(new SimpleFraction(1, 4));
        memory.add(new SimpleFraction(2, 4));
        SimpleFraction actual = memory.getFNumber();

        Assert.assertEquals(expected, actual);
        Assert.assertEquals("_On", memory.getFState());
    }

    @Test
    public void clearTest() {
        SimpleFraction expected = new SimpleFraction(0, 1);

        TMemory<SimpleFraction> memory = new TMemory<>(new SimpleFraction(1, 4));
        memory.clear();
        Assert.assertEquals("_Off", memory.getFState());
        SimpleFraction actual = memory.getFNumber();

        Assert.assertEquals(expected, actual);
    }
}
