package org.example.commands.general;

import org.example.commands.JDA.ExecuteArgs;
import org.example.commands.JDA.ICommand;

import java.util.ArrayList;

import static org.example.utils.CommandNamesConstants.HELP_COMMAND_DESC;
import static org.example.utils.CommandNamesConstants.HELP_COMMAND_NAME;

public class CommandHelp implements ICommand {



    @Override
    public void execute(ExecuteArgs var1) {

    }

    @Override
    public String getName() {
        return HELP_COMMAND_NAME;
    }

    @Override
    public String helpMessage() {
        return HELP_COMMAND_DESC;
    }

    @Override
    public boolean needOwner() {
        return false;
    }

    private void listCommands(ArrayList<ICommand> commands){




    }

}
