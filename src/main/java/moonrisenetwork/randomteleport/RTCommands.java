package moonrisenetwork.randomteleport;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RTCommands extends AbstractCommands {
    public RTCommands() {
        super("rtp");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        RandomTeleport instance = RandomTeleport.getInstance();
        ConfigManager manager = new ConfigManager("points.yml");

        String prefix = instance.getPrefix();
        FileConfiguration locationsConfig = manager.getConfig();
        FileConfiguration mainConfig = instance.getConfig();

        if (args.length == 0) {

            List<Location> locations = new ArrayList<>();

            if (locationsConfig.isSet("locations")) {

                locations = (List<Location>) locationsConfig.getList("locations");
                

                int n = new Random().nextInt(locations.size());
                player.teleport(locations.get(n));

                String locationCreated = RandomTeleport.getInstance().getConfig().getString("messages.rtp", "&7You were teleported to location with coordinates: x: {x} y: {y} z: {z}.");
                locationCreated = locationCreated.replace("{x}", String.valueOf(player.getLocation().getX()));
                locationCreated = locationCreated.replace("{y}", String.valueOf(player.getLocation().getY()));
                locationCreated = locationCreated.replace("{z}", String.valueOf(player.getLocation().getZ()));
                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',locationCreated));
            } else {
                sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',mainConfig.getString("messages.listEmpty")));
            }
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission("rtp.reload")) {
                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',RandomTeleport.getInstance().getConfig().getString(("messages.noPermission"))));
            } else {
                RandomTeleport.getInstance().reloadConfig();
                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',RandomTeleport.getInstance().getConfig().getString("messages.reload")));
            }
            return;
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (!sender.hasPermission("rtp.add")) {
                sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',RandomTeleport.getInstance().getConfig().getString("messages.noPermission")));
            } else {
                Location blockLocation = player.getLocation().getBlock().getLocation();
                List<Location> locations = new ArrayList<>();

                if (locationsConfig.isSet("locations")) {
                    locations = (List<Location>) locationsConfig.getList("locations");
                }

                if (locations.contains(blockLocation)) {
                    sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',RandomTeleport.getInstance().getConfig().getString("messages.repeatLocation")));
                }
                else{
                    locations.add(blockLocation);
                    locationsConfig.set("locations", locations);
                    manager.save();


                    String locationCreated = RandomTeleport.getInstance().getConfig().getString("messages.newLocation", "&7You add new location with coordinates: x: {x} y: {y} z: {z}.");
                    locationCreated = locationCreated.replace("{x}", String.valueOf(blockLocation.getX()));
                    locationCreated = locationCreated.replace("{y}", String.valueOf(blockLocation.getY()));
                    locationCreated = locationCreated.replace("{z}", String.valueOf(blockLocation.getZ()));
                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',locationCreated));

                }

                return;
            }
        }


        sender.sendMessage(RandomTeleport.getInstance().getConfig().getString("messages.unknownCommand"));

    }


    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return Lists.newArrayList("add", "reload");
        return Lists.newArrayList();
    }
}

