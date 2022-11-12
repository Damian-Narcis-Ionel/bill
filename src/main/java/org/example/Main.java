package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.example.commands.JDA.JDACommands;
import org.example.commands.general.CommandInfo;
import org.example.commands.general.CommandLeave;
import org.example.commands.music.*;
import org.example.utils.Constants;

import java.util.Arrays;

import static net.dv8tion.jda.api.requests.GatewayIntent.*;
import static org.example.utils.Constants.PREFIX;
import static org.example.utils.Constants.TOKEN;

public class Main {
    public static JDA jda;

    public static final GatewayIntent[] INTENTS = {GUILD_MEMBERS,
            GUILD_BANS,
            GUILD_EMOJIS_AND_STICKERS,
            GUILD_WEBHOOKS,
            GUILD_INVITES,
            GUILD_VOICE_STATES,
            GUILD_PRESENCES,
            GUILD_MESSAGES,
            GUILD_MESSAGE_REACTIONS,
            GUILD_MESSAGE_TYPING,
            DIRECT_MESSAGES,
            DIRECT_MESSAGE_REACTIONS,
            DIRECT_MESSAGE_TYPING,
            MESSAGE_CONTENT,
            SCHEDULED_EVENTS
    };

    public static void main(String[] args) {

        JDACommands jdaCommands = new JDACommands(PREFIX);
        jdaCommands.registerCommand(new CommandPlay());
        jdaCommands.registerCommand(new CommandInfo());
        jdaCommands.registerCommand(new CommandLeave());
        jdaCommands.registerCommand(new CommandPause());
        jdaCommands.registerCommand(new CommandResume());
        jdaCommands.registerCommand(new CommandQueue());
        jdaCommands.registerCommand(new CommandNowPlaying());
        jdaCommands.registerCommand(new CommandClear());
        jdaCommands.registerCommand(new CommandShuffle());

        try{

            JDA jda = JDABuilder.create(TOKEN, Arrays.asList(INTENTS))
                    .enableCache(CacheFlag.VOICE_STATE)
                    .setActivity(Activity.playing(Constants.DEFAULT_STATUS_PLAYING))
                    .setStatus(OnlineStatus.ONLINE)
                    .addEventListeners(jdaCommands)
                    .build();

        }catch (Exception e){
            e.printStackTrace();

        }


    }

}