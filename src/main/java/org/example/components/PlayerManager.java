package org.example.components;


import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.example.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {

    //TODO: sa scrie la fiecare melodie ce melodie este, repeat

    private static PlayerManager INSTANCE;
    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager(){

        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);

    }

    public GuildMusicManager getMusicManager(Guild guild){
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), guildId->{
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
            return guildMusicManager;
        });
    }

    public void loadAndPlay(TextChannel textChannel, String trackURL){
        final GuildMusicManager musicManager = this.getMusicManager(textChannel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager,trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                musicManager.scheduler.queue(audioTrack);
                StringBuilder sb = new StringBuilder();
                textChannel.sendMessage(sb.append(audioTrack.getInfo().title)
                        .append("** by **")
                        .append(audioTrack.getInfo().author)
                        .append("**"))
                        .queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                final List<AudioTrack> tracks = audioPlaylist.getTracks();

                if(trackURL.contains("ytsearch:")){
                    musicManager.scheduler.queue(tracks.get(0));
                    textChannel.sendMessage("The track" + tracks.get(0).getInfo().title  +"was added to queue")
                            .queue();
                }else{
                    if(!tracks.isEmpty()){
                        tracks.forEach(musicManager.scheduler::queue);
                        textChannel.sendMessage(musicManager.scheduler.queue.size() +" tracks added to queue")
                                .queue();
                    }
                }
            }

            @Override
            public void noMatches() {
                textChannel.sendMessage("No track was found").queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                textChannel.sendMessage("There was an error loading a track ... Skipping it ").queue();
            }
        });
    }

    public void pauseTrack(TextChannel textChannel){
        final GuildMusicManager musicManager = this.getMusicManager(textChannel.getGuild());

        if(musicManager.audioPlayer.isPaused()){
            textChannel.sendTyping().queue();
            textChannel.sendMessage("Music is already paused. Type "+ Constants.PREFIX + Constants.RESUME_COMMAND_NAME+" to resume the music").queue();
            return;
        }

        musicManager.audioPlayer.setPaused(true);
        textChannel.sendTyping().queue();
        textChannel.sendMessage("Music paused. Waiting for you to resume me ~~").queue();

    }

    public void resumeTrack(TextChannel textChannel){
        final GuildMusicManager musicManager = this.getMusicManager(textChannel.getGuild());

        if(!musicManager.audioPlayer.isPaused()){
            textChannel.sendTyping().queue();
            textChannel.sendMessage("Music is not paused.").queue();
            return;
        }

        musicManager.audioPlayer.setPaused(false);
        textChannel.sendTyping().queue();
        textChannel.sendMessage("Music resumed. Enjoy your songs!").queue();
    }


    public static PlayerManager getINSTANCE(){
        if(INSTANCE==null){
            INSTANCE=new PlayerManager();
        }
        return INSTANCE;
    }

}