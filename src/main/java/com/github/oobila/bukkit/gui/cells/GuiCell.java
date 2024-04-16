package com.github.oobila.bukkit.gui.cells;

import com.github.oobila.bukkit.gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface GuiCell {

    ItemStack getIcon();

    void onClick(InventoryClickEvent e, Player player, Cell cell, Gui gui);

}
