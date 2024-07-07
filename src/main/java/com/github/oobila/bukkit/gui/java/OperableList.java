package com.github.oobila.bukkit.gui.java;

import java.util.Collection;

public interface OperableList extends ReadOnlyList {

    boolean remove(Object o);
    boolean removeAll(Collection<?> c);
    boolean retainAll(Collection<?> c);
    void clear();

}
