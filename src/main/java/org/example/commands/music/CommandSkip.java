package org.example.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
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

import static org.example.utils.ErrorNamesConstants.*;

public class CommandSkip implements ICommand {

    private final List<String> argsNames = Arrays.asList("NONE");

    @Override
    public void execute(ExecuteArgs var1) {
        final TextChannel channel = var1.getTextChannel();
        final Member self = var1.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()) {
            channel.sendMessage(ERROR_BOT_NOT_IN_VOICE_CHANNEL).queue();
            return;
        }

        final Member member = var1.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            channel.sendMessage(ERROR_BOT_NOT_IN_VOICE_CHANNEL).queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage(ERROR_NOT_SAME_CHANNEL).queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getINSTANCE().getMusicManager(var1.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if (audioPlayer.getPlayingTrack() == null) {
            channel.sendMessage("There is no track playing currently").queue();
            return;
        }

        musicManager.scheduler.nextTrack();
        channel.sendMessage("Skipped the current track").queue();
    }

    @Override
    public String getName() {
        return CommandNamesConstants.SKIP_COMMAND_NAME;
    }

    @Override
    public String helpMessage() {
        return CommandNamesConstants.SKIP_COMMAND_DESC;
    }

    @Override
    public boolean needOwner() {
        return false;
    }

    public List<String> getArgsNames() {
        return argsNames;
    }
}
