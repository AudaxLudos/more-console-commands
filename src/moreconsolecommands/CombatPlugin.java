package moreconsolecommands;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseEveryFrameCombatPlugin;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import org.json.JSONException;
import org.json.JSONObject;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.cheatmanager.CheatTarget;
import org.lazywizard.console.commands.*;

import java.io.IOException;

public class CombatPlugin extends BaseEveryFrameCombatPlugin {
    @Override
    public void init(CombatEngineAPI engine) {
        try {
            JSONObject mccData = Global.getSettings().loadJSON(Utils.MCC_SETTINGS_FILE);

            if (Utils.getBoolean(mccData, "mcc_combat_god_mode")) {
                new God().runCommand("", BaseCommand.CommandContext.COMBAT_SIMULATION);
            }
            if (Utils.getBoolean(mccData, "mcc_combat_infinite_flux")) {
                new InfiniteFlux().runCommand("", BaseCommand.CommandContext.COMBAT_SIMULATION);
            }
            if (Utils.getBoolean(mccData, "mcc_combat_infinite_cr")) {
                new InfiniteCR().runCommand("", BaseCommand.CommandContext.COMBAT_SIMULATION);
            }
            if (Utils.getBoolean(mccData, "mcc_combat_infinite_ammo")) {
                new InfiniteAmmo().runCommand("", BaseCommand.CommandContext.COMBAT_SIMULATION);
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

        Global.getCombatEngine().removePlugin(this);
    }
}
