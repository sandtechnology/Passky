package com.rabbitcomapny;

import com.rabbitcomapny.commands.*;
import com.rabbitcomapny.listeners.*;
import com.rabbitcomapny.utils.Utils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class Passky extends JavaPlugin {

    private static Passky instance;

    public static HashMap<UUID, Boolean> isLoggedIn = new HashMap<>();
    public static HashMap<UUID, Integer> failures = new HashMap<>();
    public static HashMap<UUID, Double> damage = new HashMap<>();

    private File c = null;
    private final YamlConfiguration conf = new YamlConfiguration();

    private File m = null;
    private final YamlConfiguration mess = new YamlConfiguration();

    private File p = null;
    private final YamlConfiguration pass = new YamlConfiguration();


    private static org.apache.logging.log4j.core.Filter passwordFilter;

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Utils.chat("&7[&aPassky&7] &cPlugin is disabled!"));
    }

    public void onEnable() {
        instance = this;

        this.c = new File(getDataFolder(), "config.yml");
        this.m = new File(getDataFolder(), "messages.yml");
        this.p = new File(getDataFolder(), "passwords.yml");

        mkdirAndLoad(c, conf);
        mkdirAndLoad(m, mess);
        mkdirAndLoad(p, pass);

        if (conf.getBoolean("hide_password", true)) {
            setupPasswordFilter();
        }

        Bukkit.getConsoleSender().sendMessage(Utils.chat("&7[&aPassky&7] &aPlugin is enabled!"));

        getCommand("login").setExecutor(new Login());
        getCommand("register").setExecutor(new Register());
        getCommand("changepassword").setExecutor(new Changepass());
        getCommand("forcechangepassword").setExecutor(new ForceChangePassword());
        getCommand("forceregister").setExecutor(new ForceRegister());

        new PlayerJoinListener(this);
        new PlayerMoveListener(this);
        new PlayerDropItemListener(this);
        new PlayerPickUpItemListener(this);
        new InventoryOpenListener(this);
        new PlayerChatListener(this);
        new PlayerCommandListener(this);
        new InventoryClickListener(this);
        new PlayerDamageListener(this);
        new BlockPlaceListener(this);
        new BlockBreakListener(this);
    }

    public void setupPasswordFilter() {
        //Load main command from plugins
        YamlConfiguration pluginYaml = YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(getResource("plugin.yml")), StandardCharsets.UTF_8));
        ConfigurationSection section = pluginYaml.getConfigurationSection("commands");
        final Set<String> commandHeads = new LinkedHashSet<>();
        String pluginName = pluginYaml.getString("name");

        if (section != null) {
            for (String key : section.getKeys(false)) {
                //Handle "plugin:command" case
                commandHeads.add(pluginName + ':' + key);
                commandHeads.add(key);
                for (String aliases : section.getStringList(key + ".aliases")) {
                    commandHeads.add(pluginName + ':' + aliases);
                    commandHeads.add(aliases);
                }
            }
        }

        //make sure filter init only once
        if (passwordFilter == null) {
            passwordFilter = new org.apache.logging.log4j.core.Filter() {
                @Override
                public Result getOnMismatch() {
                    return Result.NEUTRAL;
                }

                @Override
                public Result getOnMatch() {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String s, Object... objects) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String s, Object o) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String s, Object o, Object o1) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String s, Object o, Object o1, Object o2) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String s, Object o, Object o1, Object o2, Object o3) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String s, Object o, Object o1, Object o2, Object o3, Object o4) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String s, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String s, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String s, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String s, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String s, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, Object o, Throwable throwable) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
                    return Result.NEUTRAL;
                }

                @Override
                public Result filter(LogEvent logEvent) {
                    if (logEvent.getLevel() == Level.INFO) {
                        String[] commandRecord = logEvent.getMessage().getFormattedMessage().split("issued server command: ", 2);
                        if (commandRecord.length == 2) {
                            //Remove '/'
                            String command = commandRecord[1].substring(1);
                            //Remove space, get the main command
                            command = command.split(" ", 2)[0];
                            for (String commandHead : commandHeads) {
                                if (command.equalsIgnoreCase(commandHead)) {
                                    return Result.DENY;
                                }
                            }
                        }
                    }
                    return Result.NEUTRAL;
                }

                @Override
                public State getState() {
                    return isStarted() ? State.STARTED : State.STOPPED;
                }

                @Override
                public void initialize() {

                }

                @Override
                public void start() {

                }

                @Override
                public void stop() {

                }

                @Override
                public boolean isStarted() {
                    return Passky.getInstance().isEnabled();
                }

                @Override
                public boolean isStopped() {
                    return !Passky.getInstance().isEnabled();
                }
            };
            ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(passwordFilter);
        }
    }

    private void mkdirAndLoad(File file, YamlConfiguration conf) {
        if (!file.exists()) {
            saveResource(file.getName(), false);
        } else {
            Utils.mergeYaml(file.getName(), file);
        }
        try {
            conf.load(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }


    public YamlConfiguration getConf() {
        return this.conf;
    }

    public YamlConfiguration getMess() {
        return this.mess;
    }

    public YamlConfiguration getPass() {
        return this.pass;
    }

    public void saveConf() {
        try {
            this.conf.save(this.c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMess() {
        try {
            this.mess.save(this.m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePass() {
        try {
            this.pass.save(this.p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Passky getInstance() { return instance; }
}
