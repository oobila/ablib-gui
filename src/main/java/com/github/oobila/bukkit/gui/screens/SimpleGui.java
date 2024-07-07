package com.github.oobila.bukkit.gui.screens;

import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.gui.cells.Cell;
import com.github.oobila.bukkit.gui.cells.model.NullCell;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.logging.Level;

import static com.github.oobila.bukkit.common.ABCommon.log;

public abstract class SimpleGui<T extends Cell<T>> extends Gui<T> {

    private final int screenSize;
    private int indexOffset;

    protected SimpleGui(int allocatedSize, String title, Plugin plugin, Player player) {
        super(allocatedSize, title, plugin, player);
        this.screenSize = getScreenSize(size());
        setup();
    }

    protected SimpleGui(List<T> cells, String title, Plugin plugin, Player player) {
        super(cells, title, plugin, player);
        this.screenSize = getScreenSize(size());
        setup();
    }

    private void setup() {
        if (size() > 54) {
            log(Level.SEVERE, "GUI size can only go up to 54");
            return;
        }
        indexOffset = getIndexOffset();
    }

    private static int getScreenSize(int size) {
        if (size < 10) {
            return 9;
        } else if (size < 19) {
            return 18;
        } else if (size < 28) {
            return 27;
        } else if (size < 37) {
            return 36;
        } else if (size < 46) {
            return 45;
        } else {
            return 54;
        }
    }

    private int getIndexOffset() {
        if (size() < 9) {
            int diff = screenSize - size();
            return (int) Math.ceil(diff / 2d);
        } else {
            return 0;
        }
    }

    @Override
    public Cell<?> getInventoryCell(int position) {
        if (position >= indexOffset && (position - indexOffset) < size()) {
            return get(position - indexOffset);
        } else {
            return getBlockedCell();
        }
    }

    @Override
    public int getInventorySize() {
        return screenSize;
    }

    @Override
    public NullCell getBlockedCell() {
        return new NullCell();
    }

}
