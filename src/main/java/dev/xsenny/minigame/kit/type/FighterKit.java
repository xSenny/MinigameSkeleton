package dev.xsenny.minigame.kit.type;

import dev.xsenny.minigame.MinigameSkeleton;
import dev.xsenny.minigame.kit.Kit;
import dev.xsenny.minigame.kit.KitType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class FighterKit extends Kit {


    public FighterKit(MinigameSkeleton plugin, UUID uuid) {
        super(plugin, KitType.FIGHTER, uuid);
    }


    @Override
    public void onStart(Player player) {
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 3));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){ //event handler for players that have the fighter kit.

        if (uuid.equals(e.getPlayer().getUniqueId())){
            System.out.println(e.getPlayer().getName() + " the fighter just broke a block!");
        }

    }


}
