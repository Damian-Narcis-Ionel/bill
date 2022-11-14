package org.example.commands.general;

import org.example.commands.JDA.ExecuteArgs;
import org.example.commands.JDA.ICommand;

import java.util.Arrays;
import java.util.List;

import static org.example.utils.CommandNamesConstants.LEAVE_COMMAND_DESC;
import static org.example.utils.CommandNamesConstants.LEAVE_COMMAND_NAME;

public class CommandLeave implements ICommand {

    private final List<String> argsNames = Arrays.asList("NONE");

    @Override
    public void execute(ExecuteArgs executeArgs) {

        executeArgs.getMessage().getChannel().sendTyping();

        if(executeArgs.getSelfVoiceState().inAudioChannel()){
            executeArgs.getMessage().getChannel().sendMessage("Leaving channel "+getName()+" . It was a pleasure talking to you");
            executeArgs.getGuild().getAudioManager().closeAudioConnection();
        }else{
            executeArgs.getMessage().getChannel().sendTyping();
            executeArgs.getMessage().getChannel().sendMessage("Im not in a voice channel, why you bully me? ");
        }

    }

    @Override
    public String getName() {
        return LEAVE_COMMAND_NAME;
    }

    @Override
    public String helpMessage() {
        return LEAVE_COMMAND_DESC;
    }

    @Override
    public boolean needOwner() {
        return false;
    }

    @Override
    public List<String> getArgsNames() {
        return argsNames;
    }
}
