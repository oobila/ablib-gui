package com.github.oobila.bukkit.gui.screens;

import com.github.oobila.bukkit.itemstack.ItemStackBuilder;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.function.Consumer;

import static com.github.oobila.bukkit.chat.Message.message;

@Builder
@RequiredArgsConstructor
public class TextEntryGui {

    private final String title;
    private final String originalText;
    private final Player player;
    private final Plugin plugin;
    private final Consumer<String> consumer;
    private final Consumer<AnvilGUI.StateSnapshot> onClose;

    public void open() {
        new AnvilGUI.Builder()
                .plugin(plugin)
                .title(title)
                .text(originalText)
                .itemLeft(new ItemStackBuilder(Material.NAME_TAG)
                        .setDisplayName(originalText)
                        .build())
                .onClick((slot, stateSnapshot) -> {
                    switch (slot) {
                        case AnvilGUI.Slot.INPUT_LEFT:
                            message("Click on the anvil result to complete").send(player);
                            break;
                        case AnvilGUI.Slot.OUTPUT:
                            consumer.accept(stateSnapshot.getText());
                            return List.of(AnvilGUI.ResponseAction.close());
                        default:
                            //do nothing
                            break;
                    }
                    return List.of(AnvilGUI.ResponseAction.replaceInputText(originalText));
                })
                .onClose(onClose)
                .open(player);
    }
}
