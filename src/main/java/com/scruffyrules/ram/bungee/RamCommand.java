package com.scruffyrules.ram.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class RamCommand extends Command {

    private Ram ram;

    public RamCommand(Ram ram) {
        super("ram");
        this.ram = ram;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        for (String server : ram.getProxy().getServers().keySet()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Request");
            out.writeUTF(server);
            ram.getProxy().getServerInfo(server).sendData("Ram", out.toByteArray());
        }
        commandSender.sendMessage(new TextComponent("Request sent to all servers, you will receive the info when they respond!"));
        commandSender.sendMessage(new TextComponent("ServerName: Max, Total, Free (MB)"));
        ram.lastCalled = commandSender;
    }
}
