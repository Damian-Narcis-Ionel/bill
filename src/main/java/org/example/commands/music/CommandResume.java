package org.example.commands.music;

import org.example.commands.JDA.ExecuteArgs;
import org.example.commands.JDA.ICommand;
import org.example.components.PlayerManager;

import static org.example.utils.Constants.*;

public class CommandResume implements ICommand {

    @Override
    public void execute(ExecuteArgs executeArgs) {

        if(!executeArgs.getMemberVoiceState().inAudioChannel()){
            executeArgs.getTextChannel().sendMessage(ERROR_USER_NOT_IN_VOICE_CHANNEL).queue();
            return;
        }

        PlayerManager.getINSTANCE().resumeTrack(executeArgs.getTextChannel());

    }

    @Override
    public String getName() {
        return RESUME_COMMAND_NAME;
    }

    @Override
    public String helpMessage() {
        return RESUME_COMMAND_DESC;
    }

    @Override
    public boolean needOwner() {
        return false;
    }
}
