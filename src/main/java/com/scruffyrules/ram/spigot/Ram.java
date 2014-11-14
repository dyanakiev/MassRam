package com.scruffyrules.ram.spigot;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class Ram extends JavaPlugin implements PluginMessageListener {

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {
        getServer().getMessenger().registerIncomingPluginChannel(this, "Ram", this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "Ram");
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("Ram")) {return;}
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        if (in.readUTF().equals("Request")) {
            Player outplayer = Iterables.getLast(Bukkit.getOnlinePlayers());

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF(in.readUTF());
            out.writeLong(Runtime.getRuntime().maxMemory()/1024/1024);
            out.writeLong(Runtime.getRuntime().totalMemory()/1024/1024);
            out.writeLong(Runtime.getRuntime().freeMemory()/1024/1024);
            if (outplayer != null) {
                outplayer.sendPluginMessage(this, "Ram", out.toByteArray());
            }
        }
    }
}
