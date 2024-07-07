package com.github.oobila.bukkit.gui.collection;

import com.github.oobila.bukkit.gui.cells.Cell;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface CellCollectionInterface<T extends Cell<T>> extends List<T> {

    int getAllocatedSize();
    List<ItemStack> getItemStacks();
    ItemStack[] getItemStackAsArray();

}
