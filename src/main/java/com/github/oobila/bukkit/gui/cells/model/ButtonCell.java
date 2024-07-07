package com.github.oobila.bukkit.gui.cells.model;

import com.github.oobila.bukkit.gui.GuiItemStack;
import com.github.oobila.bukkit.gui.cells.BaseButtonCell;
import lombok.experimental.SuperBuilder;
import org.bukkit.inventory.ItemStack;

@SuperBuilder
public class ButtonCell extends BaseButtonCell<ButtonCell> {

    public ButtonCell(ItemStack itemStack) {
        super(itemStack);
    }

    public abstract static class ButtonCellBuilder<C extends ButtonCell, B extends ButtonCellBuilder<C, B>>
            extends BaseButtonCell.BaseButtonCellBuilder<ButtonCell, C, B> {

        private GuiItemStack itemStack;

        public B itemStack(ItemStack itemStack) {
            this.itemStack = new GuiItemStack(itemStack);
            return (B) this;
        }

    }

}
