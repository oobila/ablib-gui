package com.github.oobila.bukkit.gui.cells;

import com.github.oobila.bukkit.gui.collection.CellCollection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Cell implements GuiCell {

    private CellCollection collection;
    private int collectionPosition;
    private int inventoryPosition;

    public void onCollectionBind(CellCollection cellCollection, int position) {
        this.collection = cellCollection;
        this.collectionPosition = position;
    }

    public void onInventoryBind(int inventoryPosition) {
        this.inventoryPosition = inventoryPosition;
    }

    public void replace(GuiCell cell) {
        collection.set(collectionPosition, cell);
    }
}