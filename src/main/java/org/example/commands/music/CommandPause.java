package org.example.commands.music;

import org.example.commands.JDA.ExecuteArgs;
import org.example.commands.JDA.ICommand;
import org.example.components.PlayerManager;
import org.example.utils.CommandNamesConstants;

import java.util.Arrays;
import java.util.List;

import static org.example.utils.ErrorNamesConstants.*;

public class CommandPause implements ICommand {

    private final List<String> argsNames = Arrays.asList("NONE");

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
        return CommandNamesConstants.PAUSE_COMMAND_NAME;
    }

    @Override
    public String helpMessage() {
        return CommandNamesConstants.PAUSE_COMMAND_DESC;
    }

    @Override
    public boolean needOwner() {
        return false;
    }

    public List<String> getArgsNames() {
        return argsNames;
    }
}
