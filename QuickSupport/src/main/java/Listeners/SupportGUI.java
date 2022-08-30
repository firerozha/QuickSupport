package Listeners;

import com.github.firerozha.quicksupport.QuickSupport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.HashMap;

public class SupportGUI implements Listener {

    public HashMap<Object, String> chatToggle = new HashMap<>();
    private final QuickSupport main;

    public SupportGUI(QuickSupport main) {
        this.main = main;
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getInventory().getName().equals(ChatColor.translateAlternateColorCodes('&',
                main.getConfig().getString("guititle")))) {
            if(e.getSlot() == 10 || e.getSlot() == 13 || e.getSlot() == 16) {
                if(e.getSlot() == 10) {
                    chatToggle.put(p.getPlayer(), "SupportSlot1");
                } else if(e.getSlot() == 13) {
                    chatToggle.put(p.getPlayer(), "SupportSlot2");
                } else if(e.getSlot() == 16) {
                    chatToggle.put(p.getPlayer(), "SupportSlot3");
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("supportask")));
                p.closeInventory();
            }
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(chatToggle.get(p) != null) {
            if(e.getMessage().equalsIgnoreCase("cancel")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getConfig().getString("supportcanceled")));
                chatToggle.clear();
                e.setCancelled(true);
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("supportsuccess")));
                String supportType = main.getConfig().getString(chatToggle.get(p) + ".name");
                String staffSupport = main.getConfig().getString("staffsupport");
                staffSupport = staffSupport.replace("{name}", p.getName());
                staffSupport = staffSupport.replace("{supportType}", supportType);
                staffSupport = staffSupport.replace("{supportDetails}", e.getMessage());
                for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if(player.hasPermission(chatToggle.get(p) + ".permission")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', staffSupport));
                    }
                }
                chatToggle.clear();
                e.setCancelled(true);
            }
        }
    }

}
