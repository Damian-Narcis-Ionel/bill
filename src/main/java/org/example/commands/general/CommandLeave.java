package org.example.commands.general;

import org.example.commands.JDA.ExecuteArgs;
import org.example.commands.JDA.ICommand;

import static org.example.utils.Constants.LEAVE_COMMAND_DESC;
import static org.example.utils.Constants.LEAVE_COMMAND_NAME;

public class CommandLeave implements ICommand {
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
}
