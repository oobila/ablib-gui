package com.github.oobila.bukkit.gui.cells;

import com.github.oobila.bukkit.chat.Message;
import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.itemstack.IItemStackProxy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class ButtonCell extends MenuItemCell implements IItemStackProxy<ButtonCell> {

    private ButtonClickAction buttonClickAction;
    private long cooldownSeconds;
    private ZonedDateTime dateTime;

    public ButtonCell(ItemStack itemStack) {
        super(itemStack);
    }

    public ButtonCell name(String name) {
        setDisplayName(ChatColor.YELLOW + name);
        return this;
    }

    public ButtonCell cooldown(long seconds) {
        this.cooldownSeconds = seconds;
        return this;
    }

    public ButtonCell onClick(ButtonClickAction action) {
        this.buttonClickAction = action;
        return this;
    }

    @Override
    public void onClick(InventoryClickEvent e, Player player, Cell cell, Gui gui) {
        ZonedDateTime now = ZonedDateTime.now();
        if (dateTime == null || cooldownSeconds <= 0 ||
                (dateTime.plusSeconds(cooldownSeconds).isBefore(now))) {
            if (buttonClickAction != null) {
                buttonClickAction.onButtonClick(e, player, (ButtonCell) cell, gui);
            }
            dateTime = now;
        } else {
            new Message("This button has a {0} second cooldown")
                    .arg(cooldownSeconds)
                    .send(player);
        }
        e.setCancelled(true);
    }

    @Override
    public ButtonCell getReturnObject() {
        return this;
    }

    public interface ButtonClickAction {
        void onButtonClick(InventoryClickEvent e, Player player, ButtonCell button, Gui gui);
    }
}
