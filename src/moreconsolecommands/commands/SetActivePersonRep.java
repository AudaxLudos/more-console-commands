package moreconsolecommands.commands;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.CoreReputationPlugin;
import moreconsolecommands.Utils;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.CommandUtils;
import org.lazywizard.console.CommonStrings;
import org.lazywizard.console.Console;

public class SetActivePersonRep implements BaseCommand {

    @Override
    public CommandResult runCommand(String args, CommandContext context) {
        if (!context.isInCampaign())
        {
            Console.showMessage(CommonStrings.ERROR_CAMPAIGN_ONLY);
            return CommandResult.WRONG_CONTEXT;
        }

        InteractionDialogAPI dialog = Global.getSector().getCampaignUI().getCurrentInteractionDialog();
        if (dialog == null) {
            Console.showMessage("You need to open an interaction dialog");
            return CommandResult.ERROR;
        }
        SectorEntityToken interactionTarget = dialog.getInteractionTarget();
        if (interactionTarget == null) {
            Console.showMessage("Interacted entity not found");
            return CommandResult.ERROR;
        }
        PersonAPI activePerson = dialog.getInteractionTarget().getActivePerson();
        if (activePerson == null) {
            Console.showMessage("You need to talk to a person");
            return CommandResult.ERROR;
        }

        if (args.isEmpty())
        {
            return CommandResult.BAD_SYNTAX;
        }

        if (!CommandUtils.isFloat(args)) {
            Console.showMessage("Interacted entity not found");
            return CommandResult.ERROR;
        }

        CoreReputationPlugin.CustomRepImpact impact = new CoreReputationPlugin.CustomRepImpact();
        impact.delta = Float.parseFloat(args) * 0.01f;
        Global.getSector().adjustPlayerReputation(
                new CoreReputationPlugin.RepActionEnvelope(CoreReputationPlugin.RepActions.CUSTOM, impact,
                        null, null, true), activePerson);

        Console.showMessage("Set relationship of " + activePerson.getName().getFullName() + " to " + args);
        return CommandResult.SUCCESS;
    }
}
