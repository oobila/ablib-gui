package com.github.oobila.bukkit.gui.cells.model;

import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.gui.cells.BaseButtonCell;
import com.github.oobila.bukkit.gui.screens.SelectionGui;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SelectionButtonCell<T> extends BaseButtonCell<SelectionButtonCell<T>> {

    @Getter
    private final T selectionObject;

    private boolean disableSelection;

    public SelectionButtonCell(T selectionObject, ItemStack itemStack) {
        super(itemStack);
        this.selectionObject = selectionObject;
    }

    @Override
    public void onClick(InventoryClickEvent e, Player player, SelectionButtonCell<T> cell, Gui<?> gui) {
        e.setCancelled(true);
        if (disableSelection) {
            return;
        }
        ((SelectionGui<T>) gui).onSelection(e, player, cell, selectionObject);
    }

    public void disableSelection() {
        disableSelection = true;
    }


}
