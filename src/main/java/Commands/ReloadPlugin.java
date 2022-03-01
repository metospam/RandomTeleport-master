package Commands;

import moonrisenetwork.randomteleport.RandomTeleport;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadPlugin extends AbstractCommands {
    public ReloadPlugin() {
        super("rtp");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        RandomTeleport instance = RandomTeleport.getInstance();
        String prefix = instance.getPrefix();

        if (args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission("rtp.reload")) {
                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',RandomTeleport.getInstance().getConfig().getString(("messages.noPermission"))));
            } else {
                RandomTeleport.getInstance().reloadConfig();
                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&',RandomTeleport.getInstance().getConfig().getString("messages.reload")));
            }
        }
    }
}
