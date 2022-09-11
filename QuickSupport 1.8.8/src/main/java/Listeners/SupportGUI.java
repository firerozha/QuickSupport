package Listeners;

import com.github.firerozha.quicksupport.QuickSupport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.List;

public class SupportGUI implements Listener {

    public HashMap<Object, String> chatToggle = new HashMap<>();
    private final QuickSupport main;

    public SupportGUI(QuickSupport main) {
        this.main = main;
    }

    // alright so here i create a method that requries you to tell me what string list to access in the config and the player to sudo to say the command in the config
    public void executeConfigCommand(int SupportSlotID, Player playerToSudo) {
        // I made a string list and i loop through it, to make the player execute it
        List<String> stringList = main.getConfig().getStringList("SupportSlot" + SupportSlotID + ".commands");
        for(String command : stringList) {
            // now we make them chat and add a slash
            playerToSudo.chat("/" + command);

        }

    }
    @EventHandler
    public void InventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&',
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
                if(chatToggle.get(p).equals("SupportSlot1")) {
                    executeConfigCommand(1, p);
                } else if(chatToggle.get(p).equals("SupportSlot2")) {
                    executeConfigCommand(2, p);
                } else if(chatToggle.get(p).equals("SupportSlot3")) {
                    executeConfigCommand(3, p);
                }
                chatToggle.clear();
                e.setCancelled(true);
            }
        }
    }

}
