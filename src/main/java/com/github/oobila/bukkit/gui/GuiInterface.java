package com.github.oobila.bukkit.gui;

import com.github.oobila.bukkit.gui.cells.model.NullCell;
import com.github.oobila.bukkit.gui.cells.Cell;

public interface GuiInterface {

    int getInventorySize();
    <T extends Cell<T>> Cell<T> getInventoryCell(int position);
    NullCell getBlockedCell();

}
