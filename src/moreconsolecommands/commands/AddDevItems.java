package moreconsolecommands.commands;

import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.Console;
import org.lazywizard.console.commands.AddSupplies;

public class AddDevItems implements BaseCommand {
    @Override
    public CommandResult runCommand(String args, CommandContext context) {
        if (!context.isInCampaign()) {
            Console.showMessage("Error: This command is campaign-only.");
            return BaseCommand.CommandResult.WRONG_CONTEXT;
        }



        return BaseCommand.CommandResult.SUCCESS;
    }
}
