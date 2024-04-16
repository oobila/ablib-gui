package com.github.oobila.bukkit.gui.cells;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import static com.github.oobila.bukkit.common.ABCommon.log;

@Getter
public class CellCollection implements CellCollectionInterface {

    @Setter
    private BlockedCell blockedCell = new BlockedCell(Material.GRAY_STAINED_GLASS_PANE);
    private final Plugin plugin;
    private final int size;
    private List<Cell> cells;
    private final Player player;

    public CellCollection(Plugin plugin, Player player, int size) {
        this.plugin = plugin;
        this.player = player;
        this.size = size;
        this.cells = new ArrayList<>(size);
    }

    public CellCollection(Plugin plugin, Player player, List<Cell> cells) {
        this.plugin = plugin;
        this.player = player;
        this.size = cells.size();
        setCells(cells);
    }

    @Override
    public Cell getCell(int position) {
        if (position < cells.size()) {
            return cells.get(position);
        } else {
            return blockedCell;
        }
    }

    @Override
    public void setCells(List<Cell> cells) {
        if(cells.size() != size){
            log(Level.SEVERE, "cell length [{0}] does not match gui size [{1}]", cells.size(), size);
            return;
        }
        this.cells = new ArrayList<>(size);
        for(int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            this.cells.add(i, cell);
            onSetCell(i, cell);
        }
    }

    @Override
    public void setCell(int position, Cell cell) {
        cells.set(position, cell);
        onSetCell(position, cell);
    }

    @Override
    public List<ItemStack> getItemStacks() {
        return cells.stream().map(Cell::getIcon).toList();
    }

    @Override
    public ItemStack[] getItemStackArray() {
        return getItemStacks().toArray(ItemStack[]::new);
    }

    private void onSetCell(int position, Cell cell) {
        if(cell == null) {
            cell = blockedCell;
        }
        cell.onCellAdd(plugin, this, position);
    }

    public static CellCollection fromItemStacks(Plugin plugin, Player player, ItemStack[] itemStacks) {
        return fromItemStacks(plugin, player, Arrays.stream(itemStacks).toList());
    }

    public static CellCollection fromItemStacks(Plugin plugin, Player player, ItemStack[] itemStacks, BlockedCell blockedCell) {
        return fromItemStacks(plugin, player, Arrays.stream(itemStacks).toList(), blockedCell);
    }

    public static CellCollection fromItemStacks(Plugin plugin, Player player, Collection<ItemStack> itemStacks) {
        return fromItemStacks(plugin, player, itemStacks, new BlockedCell());
    }

    public static CellCollection fromItemStacks(
            Plugin plugin, Player player, Collection<ItemStack> itemStacks, BlockedCell blockedCell) {
        CellCollection cellCollection = new CellCollection(plugin, player, itemStacks.size());
        cellCollection.setCells(
                new ArrayList<>(
                        itemStacks.stream().map(
                                ItemCell::new
                        ).toList()
                )
        );
        cellCollection.setBlockedCell(blockedCell);
        return cellCollection;
    }
}
