package com.ultikits.plugins;

import com.ultikits.plugins.commands.*;
import com.ultikits.plugins.config.BasicConfig;
import com.ultikits.plugins.config.JoinWelcomeConfig;
import com.ultikits.plugins.listeners.BackListener;
import com.ultikits.plugins.listeners.JoinWelcomeListener;
import com.ultikits.plugins.listeners.WhitelistListener;
import com.ultikits.ultitools.abstracts.AbstractConfigEntity;
import com.ultikits.ultitools.abstracts.UltiToolsPlugin;
import lombok.Getter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BasicFunctions extends UltiToolsPlugin {
    @Getter
    private static BasicFunctions instance;

    @Override
    public boolean registerSelf() throws IOException {
        instance = this;
        BasicConfig configEntity = getConfigManager().getConfigEntity(this, BasicConfig.class);
        if (configEntity.isEnableHeal()) {
            getCommandManager().register(new HealCommand(), "ultikits.tools.command.heal", i18n("指令治愈功能"), "heal", "h");
        }
        if (configEntity.isEnableGmChange()) {
            getCommandManager().register(new GMChangeCommand(), "ultikits.tools.command.gm", i18n("游戏模式切换功能"), "gm");
        }
        if (configEntity.isEnableBack()) {
            getCommandManager().register(new BackCommands(), "ultikits.tools.command.back", i18n("快捷返回功能"), "back");
            getListenerManager().register(this, new BackListener());
        }
        if (configEntity.isEnableRandomTeleport()) {
            getCommandManager().register(new RandomTpCommands(), "ultikits.tools.command.wild", i18n("随机传送功能"), "wild");
        }
        if (configEntity.isEnableFly()) {
            getCommandManager().register(new FlyCommands(), "ultikits.tools.command.fly", i18n("飞行功能"), "fly");
        }
        if (configEntity.isEnableWhitelist()) {
            getCommandManager().register(new WhitelistCommands(), "ultikits.tools.whitelist", i18n("白名单功能"), "wl");
            getListenerManager().register(this, new WhitelistListener());
        }
        if (configEntity.isEnableJoinWelcome()) {
            getListenerManager().register(this, new JoinWelcomeListener());
        }
        return true;
    }

    @Override
    public void unregisterSelf() {
        getCommandManager().unregister("heal");
        getCommandManager().unregister("gm");
        getCommandManager().unregister("back");
        getCommandManager().unregister("wild");
        getCommandManager().unregister("fly");
        getCommandManager().unregister("wl");
        getListenerManager().unregisterAll(this);
    }

    @Override
    public void reloadSelf() {
        getConfigManager().reloadConfigs(this);
    }

    @Override
    public List<String> supported() {
        return super.supported();
    }

    @Override
    public List<AbstractConfigEntity> getAllConfigs() {
        return Arrays.asList(
                new BasicConfig("res/config/config.yml"),
                new JoinWelcomeConfig("res/config/join.yml")
        );
    }
}
