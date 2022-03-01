package Commands;

import Config.ConfigManager;
import moonrisenetwork.randomteleport.RandomTeleport;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AddLocation extends AbstractCommands  {
    public AddLocation() {
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
                else {
                    locations.add(blockLocation);
                    locationsConfig.set("locations", locations);
                    manager.save();


                    String locationCreated = RandomTeleport.getInstance().getConfig().getString("messages.newLocation", "&7You add new location with coordinates: x: {x} y: {y} z: {z}.");
                    locationCreated = locationCreated.replace("{x}", String.valueOf(blockLocation.getX()));
                    locationCreated = locationCreated.replace("{y}", String.valueOf(blockLocation.getY()));
                    locationCreated = locationCreated.replace("{z}", String.valueOf(blockLocation.getZ()));
                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',locationCreated));

                }
            }
        }
    }
}

