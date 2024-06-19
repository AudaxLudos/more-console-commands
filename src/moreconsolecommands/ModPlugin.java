package moreconsolecommands;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import lunalib.lunaSettings.LunaSettings;
import org.json.JSONException;
import org.json.JSONObject;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.commands.DevMode;
import org.lazywizard.console.commands.InfiniteFuel;
import org.lazywizard.console.commands.InfiniteSupplies;

import java.io.IOException;

public class ModPlugin extends BaseModPlugin {
    @Override
    public void onGameLoad(boolean newGame) {
        super.onGameLoad(newGame);

        try {
            JSONObject mccData = Global.getSettings().loadJSON(Utils.MCC_SETTINGS_FILE);

            if (Utils.getBoolean(mccData, "mcc_dev_mode")) {
                if (!Global.getSettings().isDevMode()) {
                    new DevMode().runCommand("", BaseCommand.CommandContext.CAMPAIGN_MAP);
                }
            }
            if (Utils.getBoolean(mccData, "mcc_infinite_supplies")) {
                new InfiniteSupplies().runCommand("", BaseCommand.CommandContext.CAMPAIGN_MAP);
            }
            if (Utils.getBoolean(mccData, "mcc_infinite_fuel")) {
                new InfiniteFuel().runCommand("", BaseCommand.CommandContext.CAMPAIGN_MAP);
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
