package org.example.commands.music;

import org.example.commands.JDA.ExecuteArgs;
import org.example.commands.JDA.ICommand;

import static org.example.utils.Constants.ERROR_USER_NOT_IN_VOICE_CHANNEL;

public class CommandShufflePlaylist implements ICommand {


    @Override
    public void execute(ExecuteArgs executeArgs) {

        if(!executeArgs.getMemberVoiceState().inAudioChannel()){
            executeArgs.getTextChannel().sendMessage(ERROR_USER_NOT_IN_VOICE_CHANNEL).queue();
            return;
        }

       // PlayerManager.getINSTANCE().
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String helpMessage() {
        return null;
    }

    @Override
    public boolean needOwner() {
        return false;
    }
}
