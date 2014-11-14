package com.scruffyrules.ram.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class Ram extends Plugin implements Listener {

    CommandSender lastCalled;

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {
        getProxy().registerChannel("Ram");
        getProxy().getPluginManager().registerCommand(this, new RamCommand(this));
        getProxy().getPluginManager().registerListener(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getTag().equals("Ram")) {return;}
        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        String server = in.readUTF();
        long max = in.readLong();
        long total = in.readLong();
        long free = in.readLong();
        String theGoodStuff = server + ": " + max + ", " + total + ", " + free;
        lastCalled.sendMessage(new TextComponent(theGoodStuff));
    }
}
