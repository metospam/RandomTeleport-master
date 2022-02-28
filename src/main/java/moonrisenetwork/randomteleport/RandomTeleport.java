package moonrisenetwork.randomteleport;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomTeleport extends JavaPlugin {

 private static RandomTeleport instance;
 private String prefix;


 @Override
 public void onEnable() {
  instance = this;
  getConfig().options().copyDefaults(true);
  prefix = ChatColor.translateAlternateColorCodes('&',getConfig().getString("prefix", "&7[&6Random&7Teleport]: "));
  saveDefaultConfig();
 }

 @Override
 public void onDisable() {
 }


 public static RandomTeleport getInstance() {
  return instance;
 }

 public String getPrefix() {
  return prefix;
 }
}
