package org.example.commands.music;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.example.commands.JDA.ExecuteArgs;
import org.example.commands.JDA.ICommand;
import org.example.components.GuildMusicManager;
import org.example.components.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.example.utils.Constants.SHUFFLE_COMMAND_DESC;
import static org.example.utils.Constants.SHUFFLE_COMMAND_NAME;

public class CommandShuffle implements ICommand {

    @Override
    public void execute(ExecuteArgs executeArgs) {
        final TextChannel channel = executeArgs.getTextChannel();
        final GuildMusicManager musicManager = PlayerManager.getINSTANCE().getMusicManager(executeArgs.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

        if (queue.isEmpty()) {
            channel.sendMessage("The queue is currently empty").queue();
            return;
        }

        final List<AudioTrack> trackList = new ArrayList<>(queue);
        Collections.shuffle(trackList);
        queue = new LinkedBlockingQueue<>(trackList);
        musicManager.scheduler.queue=queue;

        channel.sendMessage("Queue was shuffled, enjoy!").queue();

    }

    @Override
    public String getName() {
        return SHUFFLE_COMMAND_NAME;
    }

    @Override
    public String helpMessage() {
        return SHUFFLE_COMMAND_DESC;
    }

    @Override
    public boolean needOwner() {
        return false;
    }

}
