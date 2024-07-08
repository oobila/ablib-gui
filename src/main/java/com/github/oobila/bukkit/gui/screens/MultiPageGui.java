package com.github.oobila.bukkit.gui.screens;

import com.github.oobila.bukkit.common.utils.MaterialUtil;
import com.github.oobila.bukkit.common.utils.model.BlockColor;
import com.github.oobila.bukkit.common.utils.model.ColoredMaterialType;
import com.github.oobila.bukkit.gui.Gui;
import com.github.oobila.bukkit.gui.cells.ButtonCell;
import com.github.oobila.bukkit.gui.cells.Cell;
import com.github.oobila.bukkit.gui.cells.GuiCell;
import com.github.oobila.bukkit.gui.cells.model.NullCell;
import com.github.oobila.bukkit.itemstack.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public abstract class MultiPageGui extends Gui {

    public static final int PAGE_SIZE = 45;
    private final Cell[] header = new Cell[9];
    private Material pageMaterial = Material.PAPER;
    private Material tabMaterial;
    private final int pages;
    private int pageIndex = 0;

    protected MultiPageGui(String title, Plugin plugin, Player player, BlockColor blockColor) {
        this(64, title, plugin, player, blockColor);
    }

    protected MultiPageGui(int pages, String title, Plugin plugin, Player player, BlockColor blockColor) {
        super(pages * PAGE_SIZE, title, plugin, player);
        this.pages = pages;
        this.tabMaterial = MaterialUtil.getColoredMaterial(blockColor, ColoredMaterialType.STAINED_GLASS_PANE);
        if (this.tabMaterial.equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
            this.tabMaterial = Material.WHITE_STAINED_GLASS_PANE;
        }
        updateHeader();
    }

    protected MultiPageGui(List<GuiCell> cells, String title, Plugin plugin, Player player, BlockColor blockColor) {
        super(cells, title, plugin, player);
        this.pages = (int) Math.ceil((double) cells.size() / PAGE_SIZE);
        this.tabMaterial = MaterialUtil.getColoredMaterial(blockColor, ColoredMaterialType.STAINED_GLASS_PANE);
        if (this.tabMaterial.equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
            this.tabMaterial = Material.WHITE_STAINED_GLASS_PANE;
        }
        updateHeader();
    }

    private ButtonCell getPageIcon() {
        return new ButtonCell(
                new ItemStackBuilder(pageMaterial)
                        .setDisplayName(String.format("Page %s of %s", pageIndex + 1, pages))
                        .setCount(pageIndex + 1)
                        .build()
        );
    }

    private void updateHeader() {
        for (int index = 0; index < 9; index++) {
            switch (index) {
                case 4:
                    header[index] = getPageIcon();
                    break;
                case 3:
                    header[index] = previousButton();
                    break;
                case 5:
                    header[index] = nextButton();
                    break;
                default:
                    header[index] = new NullCell();
                    break;
            }
        }
    }

    private ButtonCell previousButton() {
        return ButtonCell.builder()
                .itemStack(
                        new ItemStackBuilder(tabMaterial)
                                .setDisplayName("Previous Page")
                                .build()
                )
                .buttonClickAction((e, player, button, gui) -> {
                    MultiPageGui mpg = (MultiPageGui) gui;
                    mpg.pageIndex = mpg.pageIndex == 0 ? mpg.pageIndex : mpg.pageIndex - 1;
                    header[4] = getPageIcon();
                    mpg.reload();
                })
                .build();
    }

    private ButtonCell nextButton() {
        return ButtonCell.builder()
                .itemStack(
                        new ItemStackBuilder(tabMaterial)
                                .setDisplayName("Next Page")
                                .build()
                )
                .buttonClickAction((e, player, button, gui) -> {
                    MultiPageGui mpg = (MultiPageGui) gui;
                    mpg.pageIndex = mpg.pageIndex == (pages - 1) ? mpg.pageIndex : mpg.pageIndex + 1;
                    header[4] = getPageIcon();
                    mpg.reload();
                })
                .build();
    }

    @Override
    public int getInventorySize() {
        return 54;
    }

    @Override
    public GuiCell getInventoryCell(int position) {
        if (position < 9) {
            return header[position];
        } else if ((position - 9) < getAllocatedSize()){
            GuiCell cell = get(pageIndex * PAGE_SIZE + (position - 9));
            if (cell == null) {
                return this.getNullCell();
            } else {
                return cell;
            }
        } else {
            return this.getNullCell();
        }
    }

}
