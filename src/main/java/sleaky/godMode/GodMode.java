package sleaky.godMode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.UUID;

public final class GodMode extends JavaPlugin implements CommandExecutor, Listener {

    public static HashSet<UUID> godModePlayers = new HashSet<>();

    @Override
    public void onEnable() {
        this.getCommand("god").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to execute this command");
        }
        Player target = (Player) sender;
        if (args.length > 0) {
            target = Bukkit.getPlayer(args[0]);
        }
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player specified !");
            return true;
        }
        if (godModePlayers.contains(target.getUniqueId())) {
            godModePlayers.remove(target.getUniqueId());
            target.sendMessage("GodMode disabled");
        } else {
            godModePlayers.add(target.getUniqueId());
            target.sendMessage("GodMode enabled");
        }
        return true;

    }

    @EventHandler
    public void playerImmortal(EntityDamageEvent event) {
        Entity e = event.getEntity();
        if (godModePlayers.contains(e.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
