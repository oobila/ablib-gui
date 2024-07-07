package com.github.oobila.bukkit.gui.cells.model;

import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.gui.cells.MenuItemCell;
import com.github.oobila.bukkit.itemstack.CustomItemStack;
import com.github.oobila.bukkit.itemstack.ItemStackProxy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NullCell extends MenuItemCell<NullCell> {

    private static final ItemStack defaultItemStack = new NullItemStack(Material.GRAY_STAINED_GLASS_PANE);

    public NullCell() {
        super(defaultItemStack);
    }

    public NullCell(ItemStack itemStack) {
        super(itemStack);
    }

    public NullCell(Material material) {
        super(new NullItemStack(material));
    }

    @Override
    public void onClick(InventoryClickEvent e, Player player, NullCell cell, Gui<?> gui) {
        e.setCancelled(true);
    }

    private static class NullItemStack extends CustomItemStack {
        protected NullItemStack(Material material) {
            super(material);
            setDisplayName(" ");
            new ItemStackProxy(this)
                    .makeUnstackable();
        }
    }
}
