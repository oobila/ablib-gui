package com.github.oobila.bukkit.gui.java;

import java.util.Collection;

public interface ReadOnlyList {

    int indexOf(Object o);
    int lastIndexOf(Object o);
    int size();
    boolean isEmpty();
    boolean contains(Object o);
    Object[] toArray();
    boolean containsAll(Collection<?> c);

}
