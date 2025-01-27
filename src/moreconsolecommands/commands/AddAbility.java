package moreconsolecommands.commands;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.loading.AbilitySpecAPI;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.CommonStrings;
import org.lazywizard.console.Console;

public class AddAbility implements BaseCommand {
    @Override
    public CommandResult runCommand(String args, CommandContext context) {
        if (!context.isInCampaign()) {
            Console.showMessage(CommonStrings.ERROR_CAMPAIGN_ONLY);
            return CommandResult.WRONG_CONTEXT;
        }

        if (args.isEmpty()) {
            Console.showMessage("Please enter a valid ability Id.");
            return CommandResult.BAD_SYNTAX;
        }

        AbilitySpecAPI abilitySpec = Global.getSettings().getAbilitySpec(args);
        if (abilitySpec == null) {
            Console.showMessage("No abilities with Id: " + args);
            return CommandResult.ERROR;
        }

        Global.getSector().getCharacterData().addAbility(args);
        Console.showMessage("Added " + abilitySpec.getName() + " ability to the player");
        return CommandResult.SUCCESS;
    }
}
