package moreconsolecommands.commands;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ghosts.SensorGhostManager;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.CommonStrings;
import org.lazywizard.console.Console;

public class SpawnGhost implements BaseCommand {
    @Override
    public CommandResult runCommand(String args, CommandContext context) {
        if (!context.isInCampaign()) {
            Console.showMessage(CommonStrings.ERROR_CAMPAIGN_ONLY);
            return CommandResult.WRONG_CONTEXT;
        }

        if (!Global.getSector().getPlayerFleet().isInHyperspace()) {
            Console.showMessage("Only works in hyperspace");
            return CommandResult.WRONG_CONTEXT;
        }

        for (EveryFrameScript script: Global.getSector().getScripts()) {
            if (script instanceof SensorGhostManager) {
                ((SensorGhostManager) script).spawnGhost();
            }
        }

        Console.showMessage("Spawning random sensor ghost");
        return CommandResult.SUCCESS;
    }
}
