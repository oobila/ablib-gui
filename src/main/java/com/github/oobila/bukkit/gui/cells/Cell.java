package com.github.oobila.bukkit.gui.cells;

import lombok.Getter;
import org.bukkit.plugin.Plugin;

@Getter
public abstract class Cell implements GuiCell {

    private Plugin plugin;

    private CellCollection cellCollection;

    private int index;

    private int inventoryPosition;

    public void onCellAdd(Plugin plugin, CellCollection cellCollection, int position) {
        this.plugin = plugin;
        this.cellCollection = cellCollection;
        this.index = position;
    }

    public void onBind(int inventoryPosition) {
        this.inventoryPosition = inventoryPosition;
    }

    public void replace(Cell cell) {
        cellCollection.setCell(index, cell);
    }

}