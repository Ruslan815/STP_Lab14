import java.util.HashSet;

public class TSet <T>{

    private final HashSet<T> mySet;

    public TSet() {
        this.mySet = new HashSet<>();
    }

    public TSet(HashSet<T> someSet) {
        this.mySet = someSet;
    }

    public void clear() {
        mySet.clear();
    }

    public void add(T element) {
        // If superclass will be changed
        if (!this.contains(element)) {
            mySet.add(element);
        }
    }

    public void remove(T element) {
        // If superclass will be changed
        if (this.contains(element)) {
            mySet.remove(element);
        }
    }

    public boolean isEmpty() {
        return mySet.isEmpty();
    }

    public boolean contains(T element) {
        return mySet.contains(element);
    }

    public TSet<T> join(TSet<T> someTSet) {
        HashSet<T> newSet = new HashSet<>(mySet);
        newSet.addAll(someTSet.getMySet());
        return new TSet<>(newSet);
    }

    public TSet<T> difference(TSet<T> someTSet) {
        HashSet<T> newSet = new HashSet<>(mySet);
        newSet.removeAll(someTSet.getMySet());
        return new TSet<>(newSet);
    }

    public TSet<T> intersection(TSet<T> someTSet) {
        HashSet<T> newSet = new HashSet<>(mySet);
        newSet.retainAll(someTSet.getMySet());
        return new TSet<>(newSet);
    }

    public int size() {
        return mySet.size();
    }

    public T getElementByIndex(int index) {
        if (index >= mySet.size()) {
            throw new TSetException("Index " + index + " out of bound of TSet.");
        }

        T answerElement = null;
        int currentIndex = 0;
        for (T element: mySet) {
            if (currentIndex == index) {
                answerElement = element;
                break;
            }
            currentIndex++;
        }

        if (currentIndex == mySet.size()) {
            throw new TSetException("Element with index " + index + " not found.");
        } else {
            return answerElement;
        }
    }

    public HashSet<T> getMySet() {
        return mySet;
    }
}
