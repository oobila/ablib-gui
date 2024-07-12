package com.github.oobila.bukkit.gui.cells.model;

import com.github.oobila.bukkit.chat.Message;
import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.gui.cells.GuiCell;
import com.github.oobila.bukkit.gui.cells.MenuItemCell;
import com.github.oobila.bukkit.itemstack.IItemStackProxy;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.time.ZonedDateTime;

@SuperBuilder
public class ButtonCell extends MenuItemCell implements IItemStackProxy<ButtonCell> {

    @Setter(AccessLevel.PROTECTED)
    private ButtonClickAction buttonClickAction;
    @Setter(AccessLevel.PROTECTED)
    private Sound sound = Sound.UI_BUTTON_CLICK;
    @Setter(AccessLevel.PROTECTED)
    private float soundVolume = 1f;
    @Setter(AccessLevel.PROTECTED)
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
        clickSound(player);
    }

    @Override
    public void clickSound(Player player) {
        if (sound != null) {
            player.playSound(player.getLocation(), sound, soundVolume, 1f);
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
