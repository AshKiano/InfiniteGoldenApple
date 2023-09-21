package com.ashkiano.infinitegoldenapple;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class InfiniteGoldenApple extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getCommand("infinitegoldenapple").setExecutor(new GoldenAppleCommand());
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

    }

    public class GoldenAppleCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                ItemStack apple = new ItemStack(Material.GOLDEN_APPLE);
                ItemMeta meta = apple.getItemMeta();

                List<String> lore = new ArrayList<>();
                lore.add("InfiniteGoldenApple");

                if (meta != null) {
                    meta.setLore(lore);
                    apple.setItemMeta(meta);
                }

                player.getInventory().addItem(apple);
                player.sendMessage("You've received an InfiniteGoldenApple!");
            }

            return true;
        }
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        if (item.getType() == Material.GOLDEN_APPLE && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasLore() && meta.getLore().contains("InfiniteGoldenApple")) {
                event.setCancelled(true);

                // Apply the effects of the golden apple
                int absorptionDuration = 2 * 60 * 20;  // 2 minutes in ticks
                int regenerationDuration = 5 * 20;  // 5 seconds in ticks

                // Remove previous effects to ensure there's no stacking
                player.removePotionEffect(PotionEffectType.ABSORPTION);
                player.removePotionEffect(PotionEffectType.REGENERATION);

                // Apply new effects
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, absorptionDuration, 0));  // Absorption I
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, regenerationDuration, 1));  // Regeneration II
            }
        }
    }
}