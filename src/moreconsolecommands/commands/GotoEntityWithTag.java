package moreconsolecommands.commands;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.CommonStrings;
import org.lazywizard.console.Console;
import org.lazywizard.console.commands.GoTo;

import java.util.List;

public class GotoEntityWithTag implements BaseCommand {
    @Override
    public CommandResult runCommand(String args, CommandContext context) {
        if (!context.isInCampaign()) {
            Console.showMessage(CommonStrings.ERROR_CAMPAIGN_ONLY);
            return CommandResult.WRONG_CONTEXT;
        }

        if (args.isEmpty()) {
            Console.showMessage("Please enter a valid entity tag.");
            return CommandResult.BAD_SYNTAX;
        }

        List<SectorEntityToken> entitiesWithTag = Global.getSector().getEntitiesWithTag(args);
        if (entitiesWithTag.isEmpty()) {
            Console.showMessage("No entities found with tag: " + args);
            return CommandResult.ERROR;
        }

        SectorEntityToken firstEntityWithTag = entitiesWithTag.get(0);
        new GoTo().runCommand(firstEntityWithTag.getStarSystem().getNameWithNoType(), context);
        new GoTo().runCommand(firstEntityWithTag.getStarSystem().getId(), context);
        return CommandResult.SUCCESS;
    }
}
