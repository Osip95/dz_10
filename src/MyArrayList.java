import java.util.Arrays;

public class MyArrayList<E> {

    // поле - размер
    private int size = 0;
    // поле - начальный раазмер массива
    private int defoultCapasity = 10;
    // поле - массив с данными
    private Object[] elementData;

    // дефолтный конструктор
    public MyArrayList() {
        this.elementData = new Object[defoultCapasity];
    }

    /* перегруженный конструктор
     принимает на вход размерность массива */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity == 0) this.elementData = new Object[defoultCapasity];
        else if (initialCapacity > 0) this.elementData = new Object[initialCapacity];
        else throw new IllegalArgumentException("Недопустимый размер массива: " +
                    initialCapacity);
    }

    /* метод добовляет элемент в конец списка, внутри происохдит проверка на заполненность внутреннего массива,
     * при необходимости размер внутренего массива увеличивается с помощью метода grow() */
    public boolean add(E e) {
        if (elementData.length == size) grow();
        this.elementData[size] = e;
        size = size + 1;
        return true;
    }

    /* перегруженный вариант метода add(), принимает на вход позицию в которую надо вставить элемент,
     * если размера внутреннего массива не хватает то он увеличивается с помощью метода grow(),
     * с помощью системного метода arraycopy() происходит сдвиг на одну позицию вправо всех
     * элементов, которые находятся праве указанного индекса, таким образом остается свободная позиция для
     * переданного вторым параметром в метод элемента */
    public boolean add(int index, E e) {
        if (index > size || index < 0) throw new IndexOutOfBoundsException("Индекс элемента некоректен!");
        if (size == elementData.length) grow();
        System.arraycopy(elementData, index,
                elementData, index + 1,
                size - index);
        elementData[index] = e;
        size = size + 1;
        return true;
    }

    /* метод удаляет из списка элемент по индексу передаваему в параметр с помощью внутреннего метода
     * deletingAnElement(int index), предварительно проводит прверку на корректность введенного индекса с
     * помощью метода indexСheck(int index), возвращает удаленный элемент */
    public E remove(int index) {
        indexСheck(index);
        E oldValue = (E) elementData[index];
        deletingAnElement(index);
        return oldValue;
    }

    /* метод удаляет из списка элемент который принимает на вход, если такой есть в списке,
     * предварительно с помощью метки и циклов происходит поиск и "запомининие" индекса элемента во
     * внутреннем массиве, удаление происходит с помощью внктреннего массива deletingAnElement(int index)
     * возвращает истину если элемент удален или ложь если элемента нет в списке  */
    public boolean remove(Object o) {
        int i = 0;
        found:
        {
            if (o == null) {
                for (; i < size; i++)
                    if (elementData[i] == null)
                        break found;
            } else {
                for (; i < size; i++)
                    if (o.equals(elementData[i]))
                        break found;
            }
            return false;
        }
        deletingAnElement(i);
        return true;
    }

    /* метод очищает список путем присваивания каждому элементу массива null, и присваивания размера = 0 */
    public void clear() {
        for (int i = 0; i < elementData.length; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    /* метод вставляет элемент в список на позицию которую принимает первым параметром,
     * возвращает "затертый" элемент */
    public E set(int index, E element) {
        indexСheck(index);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    /* метод возвращает элемент по индексу */
    public E get(int index) {
        indexСheck(index);
        return (E) elementData[index];
    }

    /* метод возвращает размер листа */
    public int size() {
        return size;
    }

    /* метод увеличивает внутренний массив путем создания нового массива большей размерности и копирования
     * в него всех элементов, размерность вычесляется с помощью метода increaseLength(int oldCapacity) */
    private void grow() {
        int newCapacity = increaseLength(elementData.length);
        this.elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /* внутренний метод, используется для вычесления новой длины внутреннего массива */
    private int increaseLength(int oldCapacity) {
        int newCapacity;
        if (oldCapacity % 2 == 0) {
            newCapacity = oldCapacity + oldCapacity / 2 + 1;
        } else {
            newCapacity = oldCapacity + oldCapacity / 2;
        }
        return newCapacity;
    }

    /* внутренний метод, используется для определенния коректности веденного индекса */
    private void indexСheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Введен некоректный индекс");
        }
    }

    /*внутренний массив, удаляет элемент из внутреннего массива по индексу путем копирования в исходный массив
     * элементов которые находятся правее от указанного индекса, и присваивания последенму элементу null */
    private void deletingAnElement(int index) {
        final int newSize;
        if ((newSize = size - 1) > index)
            System.arraycopy(elementData, index + 1, elementData, index, newSize - index);
        elementData[size = newSize] = null;
    }
}
