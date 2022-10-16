package QuickSupport.Listeners;

import QuickSupport.QuickSupport;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import QuickSupport.UpdateChecker;

public class UpdateOnJoin implements Listener {

    private final QuickSupport plugin;

    public UpdateOnJoin(QuickSupport plugin) {
        this.plugin = plugin;
    }

    //on join, if the player has quicksupport.notify-update they will get a message
    // telling them their version is outdated
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.hasPermission("quicksupport.notify-update")) {
            new UpdateChecker(plugin, 104954).getVersion(version -> {
                if (plugin.getDescription().getVersion().equals(version)) {
                    p.sendMessage(ChatColor.GREEN + "[QuickSupport] No new update available.");
                } else {
                    p.sendMessage(ChatColor.RED + "[QuickSupport] A new version is available." +
                            "Download it here: https://www.spigotmc.org/resources/quicksupport-gui-plugin.104954/");
                }
            });
        }
    }
}
