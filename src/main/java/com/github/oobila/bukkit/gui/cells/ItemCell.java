package com.github.oobila.bukkit.gui.cells;

import com.github.oobila.bukkit.gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ItemCell extends Cell {

    private ItemStack itemStack;

    public ItemCell(ItemStack itemStack) {
        setItemStack(itemStack);
    }

    public void setItemStack(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    @Override
    public ItemStack getIcon() {
        return itemStack ;
    }

    @Override
    public void onClick(InventoryClickEvent e, Player player, Cell cell, Gui gui) {
        //no action is needed on an item cell
    }

    public static ItemCell newEmptyCell() {
        return new ItemCell(null);
    }
}
