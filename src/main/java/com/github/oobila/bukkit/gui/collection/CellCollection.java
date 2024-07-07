package com.github.oobila.bukkit.gui.collection;

import com.github.oobila.bukkit.gui.cells.Cell;
import com.github.oobila.bukkit.gui.java.OperableList;
import lombok.Getter;
import lombok.experimental.Delegate;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class CellCollection<T extends Cell<T>> implements CellCollectionInterface<T> {

    @Getter
    private final int allocatedSize;
    @Delegate(types = OperableList.class)
    private final List<T> cells = new ArrayList<>();

    public CellCollection(int allocatedSize) {
        this.allocatedSize = allocatedSize;
    }

    public CellCollection(List<T> cells) {
        this.allocatedSize = cells.size();
        addAll(cells);
    }

    @Override
    public T set(int index, T element) {
        if (index < allocatedSize && index > -1) {
            cells.set(index, element);
            onAdd(index, element);
        }
        return null;
    }

    @Override
    public void add(int index, T element) {
        if (index < allocatedSize && index > -1) {
            cells.add(index, element);
            onAdd(index, element);
        }
    }

    @Override
    public boolean add(T t) {
        if (cells.size() < allocatedSize) {
            this.add(cells.size(), t);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c.size() + cells.size() <= allocatedSize) {
            List<? extends T> list = new ArrayList<>(c);
            for (int i = index; i < index + c.size(); i++) {
                add(i, list.get(i));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.size() + cells.size() <= allocatedSize) {
            boolean result = true;
            List<? extends T> list = new ArrayList<>(c);
            for (T t : list) {
                result = result && add(t);
            }
            return result;
        }
        return false;
    }

    @Override
    public void addFirst(T t) {
        if (cells.size() < allocatedSize) {
            add(0, t);
        }
    }

    @Override
    public void addLast(T t) {
        if (cells.size() < allocatedSize) {
            add(cells.size(), t);
        }
    }

    public boolean nCopies(Supplier<T> supplier, int copies) {
        return addAll(Stream.generate(supplier)
                .limit(copies)
                .toList());
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        throw new UnsupportedOperationException("replaceAll is not supported by ablib-gui");
    }

    private void onAdd(int index, T element) {
        if (element != null) {
            element.onCollectionBind(this, index);
        }
    }

    @Override
    public List<ItemStack> getItemStacks() {
        return cells.stream().map(Cell::getIcon).toList();
    }

    @Override
    public ItemStack[] getItemStackAsArray() {
        return getItemStacks().toArray(ItemStack[]::new);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        cells.sort(c);
    }

    @Override
    public Spliterator<T> spliterator() {
        return cells.spliterator();
    }

    @Override
    public T getFirst() {
        return cells.getFirst();
    }

    @Override
    public T getLast() {
        return cells.getLast();
    }

    @Override
    public T removeFirst() {
        return cells.removeFirst();
    }

    @Override
    public T removeLast() {
        return cells.removeLast();
    }

    @Override
    public List<T> reversed() {
        return cells.reversed();
    }

    @Override
    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        return cells.toArray(generator);
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return cells.removeIf(filter);
    }

    @Override
    public Stream<T> stream() {
        return cells.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return cells.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        cells.forEach(action);
    }

    @Override
    public Iterator<T> iterator() {
        return cells.iterator();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return cells.toArray(a);
    }

    @Override
    public T get(int index) {
        return cells.get(index);
    }

    @Override
    public T remove(int index) {
        return cells.remove(index);
    }

    @Override
    public ListIterator<T> listIterator() {
        return cells.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return cells.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return cells.subList(fromIndex, toIndex);
    }
}
