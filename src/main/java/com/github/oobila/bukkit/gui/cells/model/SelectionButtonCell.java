package com.github.oobila.bukkit.gui.cells.model;

import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.gui.cells.ButtonCell;
import com.github.oobila.bukkit.gui.cells.GuiCell;
import com.github.oobila.bukkit.gui.screens.SelectionGui;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SelectionButtonCell<T> extends ButtonCell {

    @Getter
    private final T selectionObject;

    private boolean disableSelection;

    public SelectionButtonCell(T selectionObject, ItemStack itemStack) {
        super(itemStack);
        this.selectionObject = selectionObject;
    }

    @Override
    public void onClick(InventoryClickEvent e, Player player, GuiCell cell, Gui gui) {
        e.setCancelled(true);
        if (disableSelection) {
            return;
        }
        ((SelectionGui<T>) gui).onSelection(e, player, (SelectionButtonCell<T>) cell, selectionObject);
    }

    public void disableSelection() {
        disableSelection = true;
    }


}
