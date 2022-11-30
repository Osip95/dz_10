public class MyLinkedList<E> {

    //поле размер листа
    private int size = 0;
    //поле первый нод
    private Node<E> first;
    //поле последний нод
    private Node<E> last;

    /* метод добавляет элемент в конец списка, создается переменная l которая ссыалется на последний нод,
     * создается новый нод котрый имет ссылку на предыдущий элемент(последний нод LinkedList), а в качестве cылки на следующий нод null,
     * далее переменной last которая отвечает за последний элемент присваивается ссылка на новый нод, происходит проверка на случай если LinkedList пустой
     * то переменной first которая отвечает за первый элемент LinkedList присваивается ссылка на вновь созданный нод. Таким образов при добавлении первого
     * элемента в LinkedList first и last ссылаются на один и тот же нод. При добавлении второго элемета в LinkedList отрабатывает блок else и last уже будет ссылаться на новый
     * нод внутри которого будет две ссылки на предыдущий нод и на null */
    public boolean add(E e) {
        final MyLinkedList.Node<E> l = last; // обе переменные ссылаются на null
        final MyLinkedList.Node<E> newNode = new MyLinkedList.Node<>(l, e, null); // выделили область памяти, внутри нода
        last = newNode; // ласт ссылается на вновь созданную ячейку памяти
        if (l == null)
            first = newNode; //ссылается на вновь созданную ячейку памяти
        else
            l.next = newNode;
        size++;
        return true;
    }

    /* метод добавляет элемент в позицию которую принимает первым параметром, внутри происходит вызов 3-х внутренних методов
     *  indexСheck(index) - проверка коректности индекса
     *  node(index) - возвращает нод который сейчас отвечает за хранение элемента по указанной позиции
     *  linkBefore(element, node(index)) - создает новый нод и перезаписывает ссылки на предыдущий и последующие ноды */
    public void add(int index, E element) {
        indexСheck(index);
        linkBefore(element, node(index));
    }

    /* геттер */
    public E get(int index) {
        indexСheck(index);
        return node(index).item;
    }

    /* метод осущевствяет вставку элемента на место уже существующего, возвращает "затертый элемент" */
    public E set(int index, E element) {
        indexСheck(index);
        MyLinkedList.Node<E> x = node(index);
        E oldVal = x.item;
        x.item = element;
        return oldVal;
    }

    /* внутренний метод который производит поиск нода по индексу, поиск осущевствляется либо от первого нода к нужному,
     * либо от последнего в зависимости от того к какому из них ближе находиться нужный нод */
    private MyLinkedList.Node<E> node(int index) {
        if (index < (size >> 1)) {
            MyLinkedList.Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            MyLinkedList.Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    /*  внутренний метод используется для добавления нового элемента по индексу, на вход принимает нод который сейчас находится по данному индексу, создает новый нод
     * далее перезаписывает ссылки  */
    private void linkBefore(E e, MyLinkedList.Node<E> succ) {
        final MyLinkedList.Node<E> pred = succ.prev;
        final MyLinkedList.Node<E> newNode = new MyLinkedList.Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
    }

    /* внутренниий метод, производит проверку коректности введенного индекса */
    private void indexСheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Введен некоректный индекс");
        }
    }

    /* класс Node, хранит в себе элемент LinkedList-a и две ссылки на предыдущий и последующий элемент */
    private static class Node<E> {
        E item;
        MyLinkedList.Node<E> next;
        MyLinkedList.Node<E> prev;

        Node(MyLinkedList.Node<E> prev, E element, MyLinkedList.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
