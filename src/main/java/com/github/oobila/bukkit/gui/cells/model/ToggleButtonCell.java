package com.github.oobila.bukkit.gui.cells.model;

import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.gui.cells.ButtonCell;
import com.github.oobila.bukkit.gui.cells.GuiCell;
import lombok.experimental.SuperBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@SuperBuilder
public class ToggleButtonCell extends ButtonCell {

    private boolean enabled;
    private final ItemStack enabledItemStack;
    private final ItemStack disabledItemStack;
    private final ButtonClickAction enableClickAction;
    private final ButtonClickAction disableClickAction;

    public ToggleButtonCell(boolean enabled, ItemStack enabledItemStack, ItemStack disabledItemStack,
                            ButtonClickAction enableClickAction, ButtonClickAction disableClickAction) {
        super(enabled ? enabledItemStack : disabledItemStack);
        this.enabled = enabled;
        this.enabledItemStack = enabledItemStack;
        this.disabledItemStack = disabledItemStack;
        this.enableClickAction = enableClickAction;
        this.disableClickAction = disableClickAction;
    }

    @Override
    public ItemStack getIcon() {
        return enabled ? enabledItemStack : disabledItemStack;
    }

    @Override
    public void onClick(InventoryClickEvent e, Player player, GuiCell cell, Gui gui) {
        if (enabled) {
            disableClickAction.onButtonClick(e, player, this, gui);
            updateItemStack(disabledItemStack, player.getOpenInventory().getTopInventory());
        } else {
            enableClickAction.onButtonClick(e, player, this, gui);
            updateItemStack(enabledItemStack, player.getOpenInventory().getTopInventory());
        }
        enabled = !enabled;
        e.setCancelled(true);
    }

    public interface ButtonClickAction {
        void onButtonClick(InventoryClickEvent e, Player player, ToggleButtonCell button, Gui gui);
    }

}
