/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ytt.dijidori.discordbot.command;

import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.joda.time.DateTime;
import ytt.dijidori.discordbot.currency.ServerAdminCore;

/**
 *
 * @author Eric
 */
public class BanCommand extends AbstractCommand {

    private static final String timerID = "swTimer";

    @Override
    public String getKeyword() {
        return "Ban";
    }

    @Override
    public String getHelp() {
        return "requires user and message";
    }

    @Override
    public void runCommand(MessageReceivedEvent e) {
        String[] msgWrd = e.getMessage().getContent().split(" ");
        if (msgWrd.length < 3) {
            e.getChannel().sendMessage(getHelp());
            return;
        }

        if (!e.getTextChannel().checkPermission(e.getAuthor(), Permission.BAN_MEMBERS)) {
            System.out.println("USER DOES NOT HAVE PERMISSIONS TO BAN");
            return;
        }

        if (!ServerAdminCore.checkBotPermissions(e.getTextChannel(), Permission.BAN_MEMBERS)) {
            e.getChannel().sendMessage("Bot does not have permission to ban users");
        }

        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
        }

        for (User u : e.getMessage().getMentionedUsers()) {
            e.getGuild().getManager().ban(u, 0);
        }
        String msg = e.getAuthor().getUsername() + " banned users at " + DateTime.now().toString();
        msg += "\n" + e.getMessage().getContent();

        ServerAdminCore.getCore().logAction(e.getGuild(), msg);

    }

}
