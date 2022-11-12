package org.example.commands.music;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.example.commands.JDA.ExecuteArgs;
import org.example.commands.JDA.ICommand;
import org.example.components.GuildMusicManager;
import org.example.components.PlayerManager;
import org.example.utils.Constants;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;


import static org.example.utils.Constants.*;

public class CommandNowPlaying implements ICommand {

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
            channel.sendMessage(ERROR_NOT_SAME_CHANNEL).queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getINSTANCE().getMusicManager(executeArgs.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
            channel.sendMessage("There is no track playing currently").queue();
            return;

        }

        final AudioTrackInfo info = track.getInfo();


        channel.sendMessageFormat("Now playing `%s` by `%s` (Link: <%s>)", info.title, info.author, info.uri).queue();

    }

    @Override
    public String getName() {
        return Constants.NOWPLAYING_COMMAND_NAME;
    }

    @Override
    public String helpMessage() {
        return Constants.NOWPLAYING_COMMAND_DESC;
    }

    @Override
    public boolean needOwner() {
        return false;
    }
}
