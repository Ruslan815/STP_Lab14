import my_number.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class TMemory <T extends MyNumber> {

    private final String STATE_ON = "_On";
    private final String STATE_OFF = "_Off";

    private T FNumber;
    private String FState;

    public TMemory(T someNumber) {
        FNumber = someNumber;
        FState = STATE_OFF;
    }

    public void setFNumber(T someNumber) {
        FNumber = someNumber;
        FState = STATE_ON;
    }

    public T getFNumber() {
        FState = STATE_ON;
        return FNumber;
    }

    public void add(T someNumber) {
        Method method;
        try {
            method = someNumber.getClass().getMethod("add", someNumber.getClass());
        } catch (NoSuchMethodException e) {
            System.err.println("Class " + someNumber.getClass() + " doesn't contain the add method.");
            return;
        }

        try {
            FNumber = (T) method.invoke(FNumber, someNumber);
        } catch (NullPointerException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("The add method invocation exception.");
            return;
        }
        FState = STATE_ON;
    }

    public void clear() {
        Constructor constructor;
        try {
            constructor = FNumber.getClass().getConstructor();
        } catch (NoSuchMethodException e) {
            System.err.println("Class " + FNumber.getClass() + " doesn't contain the default constructor.");
            return;
        }

        try {
            FNumber = (T) constructor.newInstance();
        } catch (NullPointerException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            System.err.println("The clear method invocation exception.");
            return;
        }
        FState = STATE_OFF;
    }

    public String getFState() {
        return FState;
    }

    @Override
    public String toString() {
        return "TMemory{" +
                "FNumber=" + FNumber +
                ", FState='" + FState + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TMemory<?> tMemory = (TMemory<?>) o;
        return Objects.equals(FNumber, tMemory.FNumber) && Objects.equals(FState, tMemory.FState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FNumber, FState);
    }

    public static void main(String[] args) {
        TMemory<PNumber> memory = new TMemory<>(new PNumber(6, 10, 5));
        System.out.println(memory.getFNumber());

        memory.add(new PNumber(5, 10, 5));
        System.out.println(memory.getFNumber());

        memory.clear();
        System.out.println(memory.getFNumber());

        PNumber pNumber = new PNumber(15, 10, 3);
        memory.setFNumber(pNumber);
        System.out.print(memory.getFNumber());
    }
}
