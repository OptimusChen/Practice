package com.optimus.practice.commands;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
public abstract class PracticeCommand implements CommandExecutor {

    private String name;
    private String permission;
    private boolean requiresPlayer;

    public PracticeCommand(String name, String permission, boolean requiresPlayer){

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission(permission)){
            commandSender.sendMessage(ChatColor.RED + "No Permission!");
            return true;
        }

        if (requiresPlayer){
            if (commandSender instanceof Player){
                execute((Player) commandSender, strings);
            }else{
                commandSender.sendMessage(ChatColor.RED + "Only players can run this command!");
            }
        }

        execute(commandSender, strings);
        return false;
    }

    public abstract void execute(Player player, String[] args);
    public abstract void execute(CommandSender sender, String[] args);
}
