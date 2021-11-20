import org.junit.Assert;
import org.junit.Test;

public class TSetTest {

    @Test
    public void createTSetTest() {
        TSet<Integer> tSet = new TSet<>();

        Assert.assertNotNull(tSet.getMySet());
    }

    @Test
    public void clear() {
        TSet<Integer> tSet = new TSet<>();
        tSet.add(5);

        tSet.clear();

        Assert.assertEquals(0, tSet.size());
    }

    @Test
    public void add() {
        TSet<Integer> tSet = new TSet<>();
        tSet.add(5);

        Assert.assertEquals(1, tSet.size());
        Assert.assertTrue(tSet.contains(5));
    }

    @Test
    public void remove() {
        TSet<Integer> tSet = new TSet<>();
        tSet.add(5);

        Assert.assertEquals(1, tSet.size());
        tSet.remove(5);

        Assert.assertTrue(tSet.isEmpty());
    }

    @Test
    public void isEmpty() {
        TSet<Integer> tSet = new TSet<>();

        Assert.assertTrue(tSet.isEmpty());
    }

    @Test
    public void contains() {
        TSet<Integer> tSet = new TSet<>();
        tSet.add(5);

        Assert.assertTrue(tSet.contains(5));
    }

    @Test
    public void join() {
        TSet<Integer> tSet1 = new TSet<>();
        tSet1.add(5);
        TSet<Integer> tSet2 = new TSet<>();
        tSet2.add(6);

        TSet<Integer> tSet3 = tSet1.join(tSet2);

        Assert.assertEquals(2, tSet3.size());
        Assert.assertTrue(tSet3.contains(5));
        Assert.assertTrue(tSet3.contains(6));
    }

    @Test
    public void difference() {
        TSet<Integer> tSet1 = new TSet<>();
        tSet1.add(5);
        tSet1.add(6);
        TSet<Integer> tSet2 = new TSet<>();
        tSet2.add(6);

        TSet<Integer> tSet3 = tSet1.difference(tSet2);

        Assert.assertEquals(1, tSet3.size());
        Assert.assertTrue(tSet3.contains(5));
    }

    @Test
    public void intersection() {
        TSet<Integer> tSet1 = new TSet<>();
        tSet1.add(5);
        tSet1.add(6);
        TSet<Integer> tSet2 = new TSet<>();
        tSet2.add(6);
        tSet2.add(7);

        TSet<Integer> tSet3 = tSet1.intersection(tSet2);

        Assert.assertEquals(1, tSet3.size());
        Assert.assertTrue(tSet3.contains(6));
    }

    @Test
    public void size() {
        TSet<Integer> tSet1 = new TSet<>();
        tSet1.add(5);

        Assert.assertEquals(1, tSet1.size());
    }

    @Test
    public void getElementByIndex() {
        TSet<Integer> tSet1 = new TSet<>();
        tSet1.add(5);
        tSet1.add(6);
        tSet1.add(7);

        Assert.assertEquals(Integer.valueOf(5), tSet1.getElementByIndex(0));
    }
}