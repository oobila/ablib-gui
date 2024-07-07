package com.github.oobila.bukkit.gui;

import com.github.oobila.bukkit.gui.cells.model.NullCell;
import com.github.oobila.bukkit.gui.cells.Cell;
import com.github.oobila.bukkit.gui.collection.CellCollection;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

@Getter
public abstract class Gui<T extends Cell<T>> extends CellCollection<T> implements GuiInterface {

    private static final InventoryType inventoryType = InventoryType.PLAYER;
    private final String title;
    private final Plugin plugin;
    private final Player player;
    @Setter
    private NullCell nullCell = new NullCell();
    boolean awaitingUpdate = false;

    protected Gui(int allocatedSize, String title, Plugin plugin, Player player) {
        super(allocatedSize);
        this.title = title == null ? "" : title;
        this.plugin = plugin;
        this.player = player;
    }

    protected Gui(List<T> cells, String title, Plugin plugin, Player player) {
        super(cells);
        this.title = title == null ? "" : title;
        this.plugin = plugin;
        this.player = player;
    }

    protected void onGuiLoad(Player player, Inventory inventory){}

    protected void onGuiClose(Player player, Inventory inventory){}

    public Gui<T> open() {
        //create inventory
        Inventory inventory = createInventory();

        //listeners
        onGuiLoad(player, inventory);

        //set contents
        inventory.setContents(getCellIcons());
        for (int i = 0; i < inventory.getSize(); i++) {
            getInventoryCell(i).onInventoryBind(i);
        }

        //tracking
        GuiManager.lastOpenedGui.put(player, this);
        GuiManager.openGuis.put(player, this);

        player.closeInventory();
        player.openInventory(inventory);
        return this;
    }

    protected Inventory createInventory(){
        if(inventoryType.equals(InventoryType.PLAYER)){
            return Bukkit.createInventory(
                    player,
                    getInventorySize(),
                    getTitle()
            );
        } else {
            return Bukkit.createInventory(
                    player,
                    inventoryType,
                    getTitle()
            );
        }
    }

    public void reload() {
        if (GuiManager.openGuis.containsKey(player) && GuiManager.openGuis.get(player).equals(this)) {
            player.getOpenInventory().getTopInventory().setContents(getCellIcons());
        }
    }

    private ItemStack[] getCellIcons() {
        ItemStack[] itemStacks = new ItemStack[getInventorySize()];
        for (int i = 0; i < getInventorySize(); i++) {
            itemStacks[i] = getInventoryCell(i).getIcon();
        }
        return itemStacks;
    }

}