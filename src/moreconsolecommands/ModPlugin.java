package moreconsolecommands;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import org.json.JSONException;
import org.json.JSONObject;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.CommandUtils;
import org.lazywizard.console.commands.DevMode;
import org.lazywizard.console.commands.InfiniteFuel;
import org.lazywizard.console.commands.InfiniteSupplies;

import java.io.IOException;

public class ModPlugin extends BaseModPlugin {
    public static final String MCC_SETTINGS_FILE = "data/config/mcc_settings.ini";
    public static final String lunaLibId = "lunalib";

    @Override
    public void onGameLoad(boolean newGame) {
        super.onGameLoad(newGame);

        try {
            JSONObject mccData = Global.getSettings().loadJSON(MCC_SETTINGS_FILE);

            if (mccData.getBoolean("mcc_dev_mode")) {
                if (!Global.getSettings().isDevMode()) {
                    new DevMode().runCommand("", BaseCommand.CommandContext.CAMPAIGN_MAP);
                }
            }
            if (mccData.getBoolean("mcc_infinite_supplies")) {
                new InfiniteSupplies().runCommand("", BaseCommand.CommandContext.CAMPAIGN_MAP);
            }
            if (mccData.getBoolean("mcc_infinite_fuel")) {
                new InfiniteFuel().runCommand("", BaseCommand.CommandContext.CAMPAIGN_MAP);
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getBoolean(JSONObject data, String id) throws JSONException {
        if (Global.getSettings().getModManager().isModEnabled(lunaLibId)) {
//            Integer result = LunaSettings.getInt(modId, statId + "_" + index);
//            if (result == null) {
//                return 0;
//            }
//            return result;
        }
        return data.getBoolean(id);
    }
}
