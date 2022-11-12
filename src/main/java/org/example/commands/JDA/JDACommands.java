package org.example.commands.JDA;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

public class JDACommands extends ListenerAdapter {
    private ArrayList<ICommand> commands = new ArrayList();
    private final String prefix;

    public String getPrefix() {
        return this.prefix;
    }

    public JDACommands(String prefix) {
        this.prefix = prefix;
    }

    public ArrayList<ICommand> getCommands() {
        return this.commands;
    }

    public void setCommands(ArrayList<ICommand> commands) {
        this.commands = commands;
    }

    public void registerCommand(ICommand command) {
        this.commands.add(command);
    }

    private void init(MessageReceivedEvent event) {
        Iterator var2 = this.commands.iterator();

        ICommand command;
        do {
            if (!var2.hasNext()) {
                event.getChannel().sendMessage("This command wasn't recognized.").queue();
                return;
            }

            command = (ICommand)var2.next();
        } while(!event.getMessage().getContentRaw().split(" ")[0].equalsIgnoreCase(this.prefix + command.getName()));

        if (command.needOwner()) {
            if (event.getMember().isOwner()) {
                command.execute(new ExecuteArgs(event));
            } else {
                event.getChannel().sendMessage("You don't have the required permissions to use this command.");
            }
        } else {
            command.execute(new ExecuteArgs(event));
        }
    }

    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().startsWith(this.prefix)) {
            this.init(event);
        }

    }
}
