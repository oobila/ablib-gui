package com.github.oobila.bukkit.gui;

import com.github.oobila.bukkit.itemstack.ItemStackProxy;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class GuiItemStack extends ItemStack{

    private static final String GUI_KEY = "com/github/oobila/bukkit/gui";
    private static final String TRUE = "true";

    private final ItemStack cleanItemStack;

    public GuiItemStack(ItemStack itemStack) {
        super(itemStack);
        this.cleanItemStack = itemStack.clone();
        ItemStackProxy proxy = new ItemStackProxy(this);
        proxy.addMeta(GUI_KEY, TRUE);
        proxy.makeUnstackable();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GuiItemStack) {
            return super.equals(obj);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
