package com.github.oobila.bukkit.gui;

import com.github.oobila.bukkit.gui.cells.GuiCell;
import com.github.oobila.bukkit.gui.cells.model.NullCell;

public interface GuiInterface {

    int getInventorySize();
    GuiCell getInventoryCell(int position);
    NullCell getNullCell();

}
