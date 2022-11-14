package org.example.commands.JDA;

import java.util.List;

public interface ICommand {

    void execute(ExecuteArgs var1);

    String getName();

    String helpMessage();

    boolean needOwner();

    List<String> getArgsNames();

}
