package com.ultikits.ultitools.manager;

import com.ultikits.ultitools.UltiTools;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

public class CommandManager {

    public void register(CommandExecutor commandExecutor, String permission, String description, String... aliases) {
        PluginCommand command = getCommand(aliases[0], UltiTools.getInstance());

        command.setAliases(Arrays.asList(aliases));
        command.setPermission(permission);
        command.setDescription(description);
        getCommandMap().register(UltiTools.getInstance().getDescription().getName(), command);
        command.setExecutor(commandExecutor);
    }

    public void unregister(String name) {
        PluginCommand command = getCommand(name, UltiTools.getInstance());
        command.unregister(getCommandMap());
    }

    private PluginCommand getCommand(String name, Plugin plugin) {
        PluginCommand command = null;

        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return command;
    }

    private CommandMap getCommandMap() {
        CommandMap commandMap = null;

        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commandMap;
    }

}
