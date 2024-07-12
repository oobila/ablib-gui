package com.github.oobila.bukkit.gui.screens;

import com.github.oobila.bukkit.gui.cells.model.SelectionButtonCell;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectionGui<T> extends SimpleGui {

    protected SelectionGui(String title, Plugin plugin, Player player) {
        this(54, title, plugin, player);
    }

    protected SelectionGui(int allocatedSize, String title, Plugin plugin, Player player) {
        super(allocatedSize, title, plugin, player);
    }

    protected SelectionGui(List<SelectionButtonCell<T>> cells, String title, Plugin plugin, Player player) {
        super(new ArrayList<>(cells), title, plugin, player);
    }

    public abstract void onSelection(InventoryClickEvent e, Player player, SelectionButtonCell<T> cell, T object);

}
