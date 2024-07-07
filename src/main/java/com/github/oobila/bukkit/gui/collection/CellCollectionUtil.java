package com.github.oobila.bukkit.gui.collection;

import com.github.oobila.bukkit.gui.cells.model.ItemCell;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CellCollectionUtil {

    public static  CellCollection<ItemCell> fromItemStacks(ItemStack[] itemStacks) {
        return fromItemStacks(Arrays.stream(itemStacks).toList());
    }

    public static CellCollection<ItemCell> fromItemStacks(Collection<ItemStack> itemStacks) {
        CellCollection<ItemCell> cellCollection = new CellCollection<>(itemStacks.size());
        cellCollection.addAll(
                itemStacks.stream().map(
                        ItemCell::new
                ).toList()
        );
        return cellCollection;
    }

}
