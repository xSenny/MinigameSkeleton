package dev.xsenny.minigame.kit.type;

import dev.xsenny.minigame.MinigameSkeleton;
import dev.xsenny.minigame.kit.Kit;
import dev.xsenny.minigame.kit.KitType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class MinerKit extends Kit {


    public MinerKit(MinigameSkeleton plugin, UUID uuid) {
        super(plugin, KitType.MINER, uuid);
    }

    @Override
    public void onStart(Player player) {
        player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 3));
    }
}
