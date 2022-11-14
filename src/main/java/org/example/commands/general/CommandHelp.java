package org.example.commands.general;

import net.dv8tion.jda.api.EmbedBuilder;
import org.example.commands.JDA.ExecuteArgs;
import org.example.commands.JDA.ICommand;
import org.example.commands.JDA.JDACommands;
import org.example.utils.MiscConstants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.utils.CommandNamesConstants.HELP_COMMAND_DESC;
import static org.example.utils.CommandNamesConstants.HELP_COMMAND_NAME;

public class CommandHelp implements ICommand {

    private final List<String> argsNames = Arrays.asList("NONE");
    private ArrayList<ICommand> registeredCommands;
    private String toPrint = null;

    public CommandHelp(JDACommands jdaCommands) {
        this.registeredCommands = jdaCommands.getCommands();
    }

    @Override
    public void execute(ExecuteArgs executeArgs) {
        executeArgs.getMessage().getChannel().sendTyping();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setFooter("If you find any issues please report them to the creator");
        embed.setTitle("BILL's COMMANDS");
        embed.setColor(Color.BLUE);

        registeredCommands.forEach(c -> {
            toPrint = "** "+ MiscConstants.PREFIX + c.getName()+" **";
            List<String> argsNames = c.getArgsNames();

            if (!"NONE".equalsIgnoreCase(argsNames.get(0))) {

                argsNames.stream().filter(a -> !a.equalsIgnoreCase("NONE")).forEach(this::appedToPrint);
            }
            toPrint = toPrint + " " + "_ "+ c.helpMessage()+ " _";
            embed.addField(" ", toPrint,false);
        });

        executeArgs.getMessage().getChannel().sendMessageEmbeds(embed.build()).queue();
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

    @Override
    public List<String> getArgsNames() {
        return argsNames;
    }

    public void appedToPrint(String toAppend) {
        toPrint = toPrint + "<**" + toAppend + "**>";
    }

}
