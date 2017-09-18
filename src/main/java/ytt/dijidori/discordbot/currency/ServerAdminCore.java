/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ytt.dijidori.discordbot.currency;

import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.Channel;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.TextChannel;
import org.apache.commons.configuration.PropertiesConfiguration;
import ytt.dijidori.discordbot.core.ConfigCore;
import ytt.dijidori.discordbot.core.Core;

/**
 *
 * @author Eric
 */
public class ServerAdminCore {

    private static ServerAdminCore core;
    private final PropertiesConfiguration prop;
    
    private static final String LOG_CHANNEL_PROP = "channel.log.name";

    private ServerAdminCore() {
        prop = ConfigCore.getCore().getConfig("serveradmin");
        prop.setThrowExceptionOnMissing(true);
    }

    public static ServerAdminCore getCore() {
        if (core == null) {
            core = new ServerAdminCore();
        }
        return core;
    }
    
    public static boolean checkBotPermissions(Channel c, Permission p){
        return c.checkPermission(Core.api.getUserById(Core.api.getSelfInfo().getId()), p);
    }
    
    public void logAction(Guild g, String s){
        TextChannel t = null;
        for(TextChannel c : g.getTextChannels()){
            if(prop.getString(LOG_CHANNEL_PROP).equalsIgnoreCase(c.getName())){
                t = c;
                break;
            }
        }
        if(t == null){
            t = (TextChannel)g.createTextChannel(prop.getString(LOG_CHANNEL_PROP)).getChannel();
        }
        t.sendMessage(s);
    }
}
