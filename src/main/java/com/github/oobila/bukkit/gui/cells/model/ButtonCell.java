package com.github.oobila.bukkit.gui.cells.model;

import com.github.oobila.bukkit.gui.cells.BaseButtonCell;
import lombok.experimental.SuperBuilder;
import org.bukkit.inventory.ItemStack;

@SuperBuilder
public class ButtonCell extends BaseButtonCell<ButtonCell> {

    public ButtonCell(ItemStack itemStack) {
        super(itemStack);
    }

}
