package org.example.commands.music;

import org.example.commands.JDA.ExecuteArgs;
import org.example.commands.JDA.ICommand;
import org.example.components.PlayerManager;

import static org.example.utils.Constants.*;

public class CommandPause implements ICommand {

    @Override
    public void execute(ExecuteArgs executeArgs) {

        if(!executeArgs.getMemberVoiceState().inAudioChannel()){
            executeArgs.getTextChannel().sendMessage(ERROR_USER_NOT_IN_VOICE_CHANNEL).queue();
            return;
        }

        if(executeArgs.getSelfVoiceState().inAudioChannel()){
            executeArgs.getMessage().getChannel().sendTyping();
            executeArgs.getMessage().getChannel().sendMessage("Im not in a voice channel, why you bully me?");
        }

        PlayerManager.getINSTANCE().pauseTrack(executeArgs.getTextChannel());

    }

    @Override
    public String getName() {
        return PAUSE_COMMAND_NAME;
    }

    @Override
    public String helpMessage() {
        return PAUSE_COMMAND_DESC;
    }

    @Override
    public boolean needOwner() {
        return false;
    }
}
