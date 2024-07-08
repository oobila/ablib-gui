package com.github.oobila.bukkit.gui.collection;

import com.github.oobila.bukkit.gui.cells.GuiCell;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface CellCollectionInterface extends List<GuiCell> {

    int getAllocatedSize();
    List<ItemStack> getItemStacks();
    ItemStack[] getItemStackAsArray();

}
