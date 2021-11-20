import my_number.MyNumber;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TProc<T extends MyNumber> {

    private T Lop_Res;
    private T Rop;
    private TOperation operation;

    public TProc(T someNumber) {
        Lop_Res = Rop = someNumber;
        operation = TOperation.None;
    }

    public void resetProc(T someNumber) {
        Lop_Res = Rop = someNumber;
        operation = TOperation.None;
    }

    public void resetOperation() {
        operation = TOperation.None;
    }

    public void execOperation() {
        if (operation == TOperation.None) {
            throw new TProcException("TProc.execOperation: Операция не установлена.");
        }

        String methodName = switch (operation) {
            case Add -> "add";
            case Sub -> "subtract";
            case Mul -> "multiply";
            case Dvd -> "divide";
            default -> throw new TProcException("TProc.execOperation: Неизвестный код операции.");
        };

        Method method;
        try {
            method = Lop_Res.getClass().getMethod(methodName, Lop_Res.getClass());
        } catch (NoSuchMethodException e) {
            throw new TProcException("Class " + Lop_Res.getClass() + " doesn't contain the " + methodName + " method.");
        }

        try {
            Lop_Res = (T) method.invoke(Lop_Res, Rop);
        } catch (NullPointerException | IllegalAccessException | InvocationTargetException e) {
            throw new TProcException("The " + methodName + " invocation exception.");
        }
    }

    public void execFunction(TOperation functionName) {
        if (functionName == TOperation.None) {
            throw new TProcException("TProc.execFunction: Функция не установлена.");
        }

        String methodName = switch (functionName) {
            case Rev -> "inverse";
            case Sqr -> "square";
            default -> throw new TProcException("TProc.execFunction: Неизвестный код функции.");
        };

        Method method;
        try {
            method = Rop.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new TProcException("Class " + Rop.getClass() + " doesn't contain the " + methodName + " method.");
        }

        try {
            Rop = (T) method.invoke(Rop);
        } catch (NullPointerException | IllegalAccessException | InvocationTargetException e) {
            throw new TProcException("The " + methodName + " invocation exception.");
        }
    }

    public T getLop_Res() {
        return Lop_Res;
    }

    public void setLop_Res(T lop_Res) {
        Lop_Res = lop_Res;
    }

    public T getRop() {
        return Rop;
    }

    public void setRop(T rop) {
        Rop = rop;
    }

    public TOperation getOperation() {
        return operation;
    }

    public void setOperation(TOperation operation) {
        this.operation = operation;
    }
}

enum TOperation {
    None,
    Add,
    Sub,
    Mul,
    Dvd,
    Rev,
    Sqr
}

