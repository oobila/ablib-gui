package com.github.oobila.bukkit.gui.cells;

import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.gui.collection.CellCollection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface GuiCell {

    ItemStack getIcon();
    void onClick(InventoryClickEvent e, Player player, GuiCell cell, Gui gui);
    void onCollectionBind(CellCollection cellCollection, int position);
    void onInventoryBind(int inventoryPosition);
    void clickSound(Player player);

}
