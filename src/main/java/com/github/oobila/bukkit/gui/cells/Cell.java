package com.github.oobila.bukkit.gui.cells;

import com.github.oobila.bukkit.gui.collection.CellCollection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Cell<T extends Cell<T>> implements GuiCell<T> {

    private CellCollection<T> collection;
    private int collectionPosition;
    private int inventoryPosition;

    public void onCollectionBind(CellCollection<T> cellCollection, int position) {
        this.collection = cellCollection;
        this.collectionPosition = position;
    }

    public void onInventoryBind(int inventoryPosition) {
        this.inventoryPosition = inventoryPosition;
    }

    public void replace(T cell) {
        collection.set(collectionPosition, cell);
    }
}