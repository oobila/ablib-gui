package com.github.oobila.bukkit.gui.cells;

import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.gui.GuiItemStack;
import lombok.experimental.SuperBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuperBuilder
public abstract class MenuItemCell<T extends MenuItemCell<T>> extends Cell<T> {

    private GuiItemStack itemStack;

    protected MenuItemCell(ItemStack itemStack) {
        setItemStack(itemStack);
    }

    public void setItemStack(ItemStack itemStack){
        this.itemStack = new GuiItemStack(itemStack);
    }

    public ItemStack getItemStack() {
        return itemStack.getCleanItemStack();
    }

    public void updateItemStack(ItemStack itemStack, Inventory inventory) {
        setItemStack(itemStack);
        inventory.setItem(getInventoryPosition(), itemStack);
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                ((Gui<?>) getCollection()).getPlugin(),
                () ->
                        inventory.getViewers().forEach(humanEntity -> {
                            Player player = Bukkit.getPlayer(humanEntity.getUniqueId());
                            player.updateInventory();
                        })
                , 1);
    }

    @Override
    public ItemStack getIcon() {
        return itemStack;
    }

}