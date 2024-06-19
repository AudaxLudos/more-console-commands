package moreconsolecommands.commands;

import com.fs.starfarer.api.Global;
import moreconsolecommands.ModPlugin;
import org.json.JSONException;
import org.json.JSONObject;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.Console;
import org.lazywizard.console.commands.*;

import java.io.IOException;

public class AddDevItems implements BaseCommand {
    @Override
    public CommandResult runCommand(String args, CommandContext context) {
        if (!context.isInCampaign()) {
            Console.showMessage("Error: This command is campaign-only.");
            return BaseCommand.CommandResult.WRONG_CONTEXT;
        }

        try {
            JSONObject mccData = Global.getSettings().loadJSON(ModPlugin.MCC_SETTINGS_FILE);

            new AddXP().runCommand("20000000", context);
            new AddCredits().runCommand("100000000", context);
            new AddStoryPoints().runCommand("1000", context);
            new AddSkillPoints().runCommand("100", context);
            if (!mccData.getBoolean("mcc_infinite_supplies")) {
                new InfiniteSupplies().runCommand("", BaseCommand.CommandContext.CAMPAIGN_MAP);
            }
            if (!mccData.getBoolean("mcc_infinite_fuel")) {
                new InfiniteFuel().runCommand("", BaseCommand.CommandContext.CAMPAIGN_MAP);
            }
            new AllHullmods().runCommand(args, context);
            new AllWings().runCommand(args, context);
            new AllWeapons().runCommand(args, context);
            new AllBlueprints().runCommand(args, context);
            new AddShip().runCommand("onslaught", context);
            new AddShip().runCommand("paragon", context);
            new AddShip().runCommand("conquest", context);
            new AddShip().runCommand("astral", context);
            new AddShip().runCommand("dominator", context);
            new AddShip().runCommand("champion", context);
            new AddShip().runCommand("apogee", context);
            new AddShip().runCommand("mora", context);
            new AddSupplies().runCommand(args, context);
            new AddFuel().runCommand(args, context);
            new AddMarines().runCommand("2000", context);
            new AddMarinesXP().runCommand("2000", context);
            new AddCrew().runCommand(args, context);
            new Repair().runCommand(args, context);
            new Storage().runCommand(args, context);
            if (!Global.getSettings().isDevMode()) {
                new DevMode().runCommand(args, context);
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

        return BaseCommand.CommandResult.SUCCESS;
    }
}
