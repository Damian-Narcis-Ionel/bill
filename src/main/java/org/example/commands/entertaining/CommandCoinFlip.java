package org.example.commands.entertaining;

import org.example.commands.JDA.ExecuteArgs;
import org.example.commands.JDA.ICommand;

import static org.example.utils.CommandNamesConstants.COINFLIP_COMMAND;
import static org.example.utils.CommandNamesConstants.COINFLIP_DESC;

public class CommandCoinFlip implements ICommand {
    @Override
    public void execute(ExecuteArgs var1) {

        var1.getMessage().getChannel().sendTyping();

        StringBuilder sb = new StringBuilder();
        sb.append("The fate has spoken, the coin was flipped and it was ...");

        var1.getMessage().getChannel().sendMessage(sb).queue();

        if(Math.random()< .5){
            var1.getMessage().getChannel().sendMessage("HEAD").queue();
        }else{
            var1.getMessage().getChannel().sendMessage("TAIL").queue();
        }

    }

    @Override
    public String getName() {
        return COINFLIP_COMMAND;
    }

    @Override
    public String helpMessage() {
        return COINFLIP_DESC;
    }

    @Override
    public boolean needOwner() {
        return false;
    }
}
