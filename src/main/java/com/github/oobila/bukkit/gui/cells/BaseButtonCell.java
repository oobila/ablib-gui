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
public abstract class BaseButtonCell<T extends BaseButtonCell<T>> extends MenuItemCell<T> implements IItemStackProxy<BaseButtonCell<T>> {

    private ButtonClickAction<T> buttonClickAction;
    private long cooldownSeconds;
    private ZonedDateTime dateTime;

    protected BaseButtonCell(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public void onClick(InventoryClickEvent e, Player player, T cell, Gui<?> gui) {
        ZonedDateTime now = ZonedDateTime.now();
        if (dateTime == null || cooldownSeconds <= 0 ||
                (dateTime.plusSeconds(cooldownSeconds).isBefore(now))) {
            if (buttonClickAction != null) {
                buttonClickAction.onButtonClick(e, player, cell, gui);
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
    public BaseButtonCell<T> getReturnObject() {
        return this;
    }

    public interface ButtonClickAction<T extends BaseButtonCell<T>> {
        void onButtonClick(InventoryClickEvent e, Player player, BaseButtonCell<T> button, Gui<?> gui);
    }
}
