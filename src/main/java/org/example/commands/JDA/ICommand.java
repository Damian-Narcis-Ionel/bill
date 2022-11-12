package org.example.commands.JDA;

public interface ICommand {

    void execute(ExecuteArgs var1);

    String getName();

    String helpMessage();

    boolean needOwner();

}
