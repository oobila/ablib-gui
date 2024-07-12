package com.github.oobila.bukkit.gui.collection;

import com.github.oobila.bukkit.gui.cells.GuiCell;
import com.github.oobila.bukkit.gui.java.OperableList;
import lombok.Getter;
import lombok.experimental.Delegate;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

public class CellCollection implements CellCollectionInterface {

    @Getter
    private final int allocatedSize;
    @Delegate(types = OperableList.class)
    private final List<GuiCell> cells = new ArrayList<>();

    public CellCollection(int allocatedSize) {
        this.allocatedSize = allocatedSize;
        //will add later
    }

    public CellCollection(List<GuiCell> cells) {
        this.allocatedSize = cells.size();
        this.cells.addAll(cells);
    }

    @Override
    public GuiCell set(int index, GuiCell element) {
        if (index < allocatedSize && index > -1) {
            cells.set(index, element);
            onAdd(index, element);
        }
        return null;
    }

    @Override
    public void add(int index, GuiCell element) {
        if (index < allocatedSize && index > -1) {
            cells.add(index, element);
            onAdd(index, element);
        }
    }

    @Override
    public boolean add(GuiCell t) {
        if (cells.size() < allocatedSize) {
            this.add(cells.size(), t);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends GuiCell> c) {
        if (c.size() + cells.size() <= allocatedSize) {
            List<? extends GuiCell> list = new ArrayList<>(c);
            for (int i = index; i < index + c.size(); i++) {
                add(i, list.get(i));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends GuiCell> c) {
        if (c.size() + cells.size() <= allocatedSize) {
            boolean result = true;
            List<? extends GuiCell> list = new ArrayList<>(c);
            for (GuiCell t : list) {
                result = result && add(t);
            }
            return result;
        }
        return false;
    }

    @Override
    public void addFirst(GuiCell t) {
        if (cells.size() < allocatedSize) {
            add(0, t);
        }
    }

    @Override
    public void addLast(GuiCell t) {
        if (cells.size() < allocatedSize) {
            add(cells.size(), t);
        }
    }

    public boolean nCopies(Supplier<GuiCell> supplier, int copies) {
        return addAll(Stream.generate(supplier)
                .limit(copies)
                .toList());
    }

    @Override
    public void replaceAll(UnaryOperator<GuiCell> operator) {
        throw new UnsupportedOperationException("replaceAll is not supported by ablib-gui");
    }

    private void onAdd(int index, GuiCell element) {
        if (element != null) {
            element.onCollectionBind(this, index);
        }
    }

    @Override
    public List<ItemStack> getItemStacks() {
        return cells.stream().map(GuiCell::getIcon).toList();
    }

    @Override
    public ItemStack[] getItemStackAsArray() {
        return getItemStacks().toArray(ItemStack[]::new);
    }

    @Override
    public void sort(Comparator<? super GuiCell> c) {
        cells.sort(c);
    }

    @Override
    public Spliterator<GuiCell> spliterator() {
        return cells.spliterator();
    }

    @Override
    public GuiCell getFirst() {
        return cells.getFirst();
    }

    @Override
    public GuiCell getLast() {
        return cells.getLast();
    }

    @Override
    public GuiCell removeFirst() {
        return cells.removeFirst();
    }

    @Override
    public GuiCell removeLast() {
        return cells.removeLast();
    }

    @Override
    public List<GuiCell> reversed() {
        return cells.reversed();
    }

    @Override
    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        return cells.toArray(generator);
    }

    @Override
    public boolean removeIf(Predicate<? super GuiCell> filter) {
        return cells.removeIf(filter);
    }

    @Override
    public Stream<GuiCell> stream() {
        return cells.stream();
    }

    @Override
    public Stream<GuiCell> parallelStream() {
        return cells.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super GuiCell> action) {
        cells.forEach(action);
    }

    @Override
    public Iterator<GuiCell> iterator() {
        return cells.iterator();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return cells.toArray(a);
    }

    @Override
    public GuiCell get(int index) {
        return cells.get(index);
    }

    @Override
    public GuiCell remove(int index) {
        return cells.remove(index);
    }

    @Override
    public ListIterator<GuiCell> listIterator() {
        return cells.listIterator();
    }

    @Override
    public ListIterator<GuiCell> listIterator(int index) {
        return cells.listIterator(index);
    }

    @Override
    public List<GuiCell> subList(int fromIndex, int toIndex) {
        return cells.subList(fromIndex, toIndex);
    }
}
