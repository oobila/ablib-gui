package com.github.oobila.bukkit.gui.screens;

import com.github.oobila.bukkit.common.utils.MaterialUtil;
import com.github.oobila.bukkit.common.utils.model.BlockColor;
import com.github.oobila.bukkit.common.utils.model.ColoredMaterialType;
import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.gui.cells.ButtonCell;
import com.github.oobila.bukkit.gui.cells.Cell;
import com.github.oobila.bukkit.gui.cells.CellCollection;
import com.github.oobila.bukkit.gui.cells.CellCollectionInterface;
import com.github.oobila.bukkit.itemstack.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.logging.Level;

import static com.github.oobila.bukkit.common.ABCommon.log;

public class MultiPageGui extends Gui implements CellCollectionInterface {

    public static final int PAGE_SIZE = 45;
    public static final int _2_PAGES = 90;
    public static final int _3_PAGES = 135;
    public static final int _4_PAGES = 180;
    public static final int _5_PAGES = 225;
    public static final int _6_PAGES = 270;
    public static final int _7_PAGES = 315;
    public static final int _8_PAGES = 360;
    public static final int _9_PAGES = 405;
    public static final int MAX_SIZE = 405;

    private final ButtonCell[] pageIcons = new ButtonCell[9];
    private final ButtonCell[] selectedPageIcons = new ButtonCell[9];
    private Material selectedPageMaterial = Material.BLACK_STAINED_GLASS_PANE;
    private Material pageMaterial;
    private int pages;
    private int pageIndex = 0;

    public MultiPageGui(Plugin plugin, Player player, String title, CellCollection cellCollection) {
        this(plugin, player, title, cellCollection, BlockColor.WHITE);
    }

    public MultiPageGui(Plugin plugin, Player player, String title, CellCollection cellCollection, BlockColor blockColor) {
        super(plugin, player, title, cellCollection);

        if (cellCollection.getSize() > MAX_SIZE) {
            log(Level.SEVERE, "Cell collection is too big for this multi-page GUI");
            return;
        }

        this.pages = (int) Math.ceil((cellCollection.getSize()) / 45d);
        this.pageMaterial = MaterialUtil.getColoredMaterial(blockColor, ColoredMaterialType.STAINED_GLASS_PANE);
        if (blockColor.equals(BlockColor.BLACK)) {
            this.selectedPageMaterial = Material.WHITE_STAINED_GLASS_PANE;
        } else if (blockColor.equals(BlockColor.LIGHT_GRAY)) {
            this.pageMaterial = Material.WHITE_STAINED_GLASS_PANE;
        }
        setPageButtons();
    }

    private void setPageButtons() {
        for (int i = 0; i < 9; i++) {
            final int index = i;
            pageIcons[i] = new ButtonCell(
                    new ItemStackBuilder(pageMaterial)
                            .setDisplayName("Page " + (i + 1))
                            .setCount(i + 1)
                            .build()
            ).onClick((e, player, button, gui) -> {
                MultiPageGui mpg = (MultiPageGui) gui;
                mpg.pageIndex = index;
                mpg.reload();
            });
            selectedPageIcons[i] = new ButtonCell(
                    new ItemStackBuilder(selectedPageMaterial)
                            .setDisplayName("Page " + (i + 1))
                            .setLore(Collections.singletonList("Current page"))
                            .setCount(i + 1)
                            .build()
            ).onClick((e, player, button, gui) -> {
                MultiPageGui mpg = (MultiPageGui) gui;
                mpg.pageIndex = index;
                mpg.reload();
            });
        }
    }

    @Override
    public int getInventorySize() {
        return 54;
    }

    @Override
    public Cell getInventoryCell(int position) {
        if (position < 9) {
            if (pages > (position)) {
                if (pageIndex == position) {
                    return selectedPageIcons[pageIndex];
                } else {
                    return pageIcons[position];
                }
            } else {
                return getBlockedCell();
            }
        } else if ((position - 9) < getSize()){
            return getCell(pageIndex * PAGE_SIZE + (position - 9));
        } else {
            return getBlockedCell();
        }
    }

    @Override
    public ItemStack[] getCellIcons() {
        ItemStack[] itemStacks = new ItemStack[54];
        for (int i = 0; i < 54; i++) {
            itemStacks[i] = getInventoryCell(i).getIcon();
        }
        return itemStacks;
    }
}
