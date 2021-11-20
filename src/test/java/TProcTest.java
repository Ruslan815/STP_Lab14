import my_number.ComplexNumber;
import my_number.PNumber;
import my_number.SimpleFraction;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TProcTest {

    @Test
    public void createTProc() {
        SimpleFraction fraction = new SimpleFraction(0, 1);
        TProc<SimpleFraction> proc = new TProc<>(fraction);

        Assert.assertEquals(fraction, proc.getLop_Res());
        Assert.assertEquals(fraction, proc.getRop());
        Assert.assertEquals(TOperation.None, proc.getOperation());
    }

    @Test
    public void resetProc() {
        SimpleFraction fraction = new SimpleFraction(0, 1);
        TProc<SimpleFraction> proc = new TProc<>(fraction);

        proc.resetProc(fraction);

        Assert.assertEquals(fraction, proc.getLop_Res());
        Assert.assertEquals(fraction, proc.getRop());
        Assert.assertEquals(TOperation.None, proc.getOperation());
    }

    @Test
    public void resetOperation() {
        SimpleFraction fraction = new SimpleFraction(0, 1);
        TProc<SimpleFraction> proc = new TProc<>(fraction);

        proc.resetOperation();

        Assert.assertEquals(TOperation.None, proc.getOperation());
    }

    @Test
    public void execOperation() {
        ComplexNumber complexNumber1 = new ComplexNumber(1, 2);
        ComplexNumber complexNumber2 = new ComplexNumber(3, 5);
        TProc<ComplexNumber> proc = new TProc<>(new ComplexNumber());

        proc.setLop_Res(complexNumber1);
        proc.setRop(complexNumber2);
        proc.setOperation(TOperation.Add);
        proc.execOperation();

        Assert.assertEquals(new ComplexNumber(4, 7), proc.getLop_Res());
    }

    @Test(expected = TProcException.class)
    public void execNoneOperation() {
        TProc<ComplexNumber> proc = new TProc<>(new ComplexNumber());

        proc.execOperation();
    }

    @Test
    public void execFunction() {
        SimpleFraction simpleFraction = new SimpleFraction(1, 2);
        TProc<SimpleFraction> proc = new TProc<>(simpleFraction);

        proc.execFunction(TOperation.Sqr);

        Assert.assertEquals(new SimpleFraction(1, 4), proc.getRop());
    }

    @Test(expected = TProcException.class)
    public void execNoneFunction() {
        TProc<ComplexNumber> proc = new TProc<>(new ComplexNumber());

        proc.execFunction(TOperation.Add);
    }

    @Test
    public void getLop_Res() {
        SimpleFraction simpleFraction = new SimpleFraction(1, 2);
        TProc<SimpleFraction> proc = new TProc<>(simpleFraction);

        Assert.assertEquals(simpleFraction, proc.getLop_Res());
    }

    @Test
    public void setLop_Res() {
        SimpleFraction simpleFraction = new SimpleFraction(1, 2);
        TProc<SimpleFraction> proc = new TProc<>(simpleFraction);

        SimpleFraction simpleFraction1 = new SimpleFraction(5, 3);
        proc.setLop_Res(simpleFraction1);

        Assert.assertEquals(simpleFraction1, proc.getLop_Res());
    }

    @Test
    public void getRop() {
        SimpleFraction simpleFraction = new SimpleFraction(1, 2);
        TProc<SimpleFraction> proc = new TProc<>(simpleFraction);

        Assert.assertEquals(simpleFraction, proc.getRop());
    }

    @Test
    public void setRop() {
        SimpleFraction simpleFraction = new SimpleFraction(1, 2);
        TProc<SimpleFraction> proc = new TProc<>(simpleFraction);

        SimpleFraction simpleFraction1 = new SimpleFraction(5, 3);
        proc.setRop(simpleFraction1);

        Assert.assertEquals(simpleFraction1, proc.getRop());
    }

    @Test
    public void getOperation() {
        SimpleFraction simpleFraction = new SimpleFraction(1, 2);
        TProc<SimpleFraction> proc = new TProc<>(simpleFraction);

        Assert.assertEquals(TOperation.None, proc.getOperation());
    }

    @Test
    public void setOperation() {
        SimpleFraction simpleFraction = new SimpleFraction(1, 2);
        TProc<SimpleFraction> proc = new TProc<>(simpleFraction);

        proc.setOperation(TOperation.Mul);

        Assert.assertEquals(TOperation.Mul, proc.getOperation());
    }
}