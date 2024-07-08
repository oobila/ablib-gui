package com.github.oobila.bukkit.gui.cells;

import com.github.oobila.bukkit.chat.Message;
import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.itemstack.IItemStackProxy;
import lombok.experimental.SuperBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.time.ZonedDateTime;

@SuperBuilder
public class ButtonCell extends MenuItemCell implements IItemStackProxy<ButtonCell> {

    private ButtonClickAction buttonClickAction;
    private long cooldownSeconds;
    private ZonedDateTime dateTime;

    public ButtonCell(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public void onClick(InventoryClickEvent e, Player player, GuiCell cell, Gui gui) {
        if (cell instanceof ButtonCell buttonCell) {
            ZonedDateTime now = ZonedDateTime.now();
            if (dateTime == null || cooldownSeconds <= 0 ||
                    (dateTime.plusSeconds(cooldownSeconds).isBefore(now))) {
                if (buttonClickAction != null) {
                    buttonClickAction.onButtonClick(e, player, buttonCell, gui);
                }
                dateTime = now;
            } else {
                new Message("This button has a {0} second cooldown")
                        .arg(cooldownSeconds)
                        .send(player);
            }
            e.setCancelled(true);
        }
    }

    @Override
    public ButtonCell getReturnObject() {
        return this;
    }

    public interface ButtonClickAction {
        void onButtonClick(InventoryClickEvent e, Player player, ButtonCell buttonCell, Gui gui);
    }
}
