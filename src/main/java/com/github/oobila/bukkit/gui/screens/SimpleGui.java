package com.github.oobila.bukkit.gui.screens;

import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.gui.cells.Cell;
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
        this.screenSize = getScreenSize(getAllocatedSize());
        setup();
    }

    protected SimpleGui(List<T> cells, String title, Plugin plugin, Player player) {
        super(cells, title, plugin, player);
        this.screenSize = getScreenSize(getAllocatedSize());
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
        if (getAllocatedSize() < 9) {
            int diff = screenSize - getAllocatedSize();
            return (int) Math.ceil(diff / 2d);
        } else {
            return 0;
        }
    }

    @Override
    public <S extends Cell<S>> S getInventoryCell(int position) {
        if (position >= indexOffset && (position - indexOffset) < getAllocatedSize()) {
            S cell = (S) get(position - indexOffset);
            if (cell == null) {
                return (S) this.getNullCell();
            } else {
                return cell;
            }
        } else {
            return (S) this.getNullCell();
        }
    }

    @Override
    public int getInventorySize() {
        return screenSize;
    }

}
