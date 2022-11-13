package org.example.commands.music;


import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.example.commands.JDA.ExecuteArgs;
import org.example.commands.JDA.ICommand;
import org.example.components.GuildMusicManager;
import org.example.components.PlayerManager;
import org.example.utils.CommandNamesConstants;

import java.util.Arrays;
import java.util.List;

import static org.example.utils.ErrorNamesConstants.ERROR_BOT_NOT_IN_VOICE_CHANNEL;
import static org.example.utils.ErrorNamesConstants.ERROR_USER_NOT_IN_VOICE_CHANNEL;

public class CommandClear implements ICommand {

    private final List<String> argsNames = Arrays.asList("NONE");

    @Override
    public void execute(ExecuteArgs executeArgs) {
        final TextChannel channel = executeArgs.getTextChannel();
        final Member self = executeArgs.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            channel.sendMessage(ERROR_BOT_NOT_IN_VOICE_CHANNEL).queue();
            return;
        }

        final Member member = executeArgs.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            channel.sendMessage(ERROR_USER_NOT_IN_VOICE_CHANNEL).queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You need to be in the same voice channel as me for this to work").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getINSTANCE().getMusicManager(executeArgs.getGuild());

        musicManager.scheduler.queue.clear();

        channel.sendMessage("The queue has been cleared").queue();
    }

    @Override
    public String getName() {
        return CommandNamesConstants.CLEAR_COMMAND_NAME;
    }

    @Override
    public String helpMessage() {
        return CommandNamesConstants.CLEAR_COMMAND_DESC;
    }

    @Override
    public boolean needOwner() {
        return false;
    }

    public List<String> getArgsNames() {
        return argsNames;
    }
}
