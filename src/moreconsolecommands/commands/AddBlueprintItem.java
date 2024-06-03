package moreconsolecommands.commands;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.impl.campaign.ids.Items;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;
import com.fs.starfarer.api.loading.IndustrySpecAPI;
import com.fs.starfarer.api.loading.WeaponSpecAPI;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.CommandUtils;
import org.lazywizard.console.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddBlueprintItem implements BaseCommand {
    @Override
    public CommandResult runCommand(String args, CommandContext context) {
        if (!context.isCampaignAccessible()) {
            Console.showMessage("Error: This command is campaign-only.");
            return BaseCommand.CommandResult.WRONG_CONTEXT;
        }
        if (args.isEmpty()) {
            return CommandResult.BAD_SYNTAX;
        }

        String blueprintId = getBlueprintId(args);
        String blueprintType = getModSpecType(blueprintId);

        if (blueprintId == null) {
            Console.showMessage("No blueprint found with name or id: " + args);
            return CommandResult.ERROR;
        }
        if (blueprintType == null) {
            Console.showMessage("No blueprint found with name or id: " + args);
            return CommandResult.ERROR;
        }

        Global.getSector().getPlayerFleet().getCargo().addSpecial(new SpecialItemData(blueprintType, blueprintId), 1);

        Console.showMessage("Added " + blueprintId + " blueprint item to player fleet");
        return BaseCommand.CommandResult.SUCCESS;
    }

    public String getBlueprintId(String specId) {
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (FighterWingSpecAPI spec : Global.getSettings().getAllFighterWingSpecs()) {
            ids.add(spec.getId());
            names.add(spec.getWingName());
        }
        for (WeaponSpecAPI spec : Global.getSettings().getAllWeaponSpecs()) {
            ids.add(spec.getWeaponId());
            names.add(spec.getWeaponName());
        }
        for (ShipHullSpecAPI spec : Global.getSettings().getAllShipHullSpecs()) {
            ids.add(spec.getHullId());
            names.add(spec.getHullName());
        }
        for (IndustrySpecAPI spec : Global.getSettings().getAllIndustrySpecs()) {
            ids.add(spec.getId());
            names.add(spec.getName());
        }
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            ids.add(spec.getId());
            names.add(spec.getDisplayName());
        }
        String bestMatchIdUsingInput = CommandUtils.findBestStringMatch(specId, ids);
        String bestMatchIdUsingName = CommandUtils.findBestStringMatch(specId, names);

        for (FighterWingSpecAPI spec : Global.getSettings().getAllFighterWingSpecs()) {
            if (Objects.equals(bestMatchIdUsingName, spec.getWingName())) {
                bestMatchIdUsingName = spec.getId();
            }
        }
        for (WeaponSpecAPI spec : Global.getSettings().getAllWeaponSpecs()) {
            if (Objects.equals(bestMatchIdUsingName, spec.getWeaponName())) {
                bestMatchIdUsingName = spec.getWeaponId();
            }
        }
        for (ShipHullSpecAPI spec : Global.getSettings().getAllShipHullSpecs()) {
            if (Objects.equals(bestMatchIdUsingName, spec.getHullName())) {
                bestMatchIdUsingName = spec.getHullId();
            }
        }
        for (IndustrySpecAPI spec : Global.getSettings().getAllIndustrySpecs()) {
            if (Objects.equals(bestMatchIdUsingName, spec.getName())) {
                bestMatchIdUsingName = spec.getId();
            }
        }
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            if (Objects.equals(bestMatchIdUsingName, spec.getDisplayName())) {
                bestMatchIdUsingName = spec.getId();
            }
        }

        String bestMatchResult;
        if (bestMatchIdUsingInput == null) {
            bestMatchResult = bestMatchIdUsingName;
        } else if (bestMatchIdUsingName == null) {
            bestMatchResult = bestMatchIdUsingInput;
        } else if (!bestMatchIdUsingName.equals(bestMatchIdUsingInput)) {
            bestMatchResult = bestMatchIdUsingName;
        } else {
            bestMatchResult = bestMatchIdUsingInput;
        }

        return bestMatchResult;
    }

    public String getModSpecType(String specId) {
        String type = "";
        for (FighterWingSpecAPI f : Global.getSettings().getAllFighterWingSpecs()) {
            if (Objects.equals(f.getId(), specId)) {
                type = Items.FIGHTER_BP;
            }
        }

        for (WeaponSpecAPI w : Global.getSettings().getAllWeaponSpecs()) {
            if (Objects.equals(w.getWeaponId(), specId)) {
                type = Items.WEAPON_BP;
            }
        }

        for (ShipHullSpecAPI s : Global.getSettings().getAllShipHullSpecs()) {
            if (Objects.equals(s.getHullId(), specId)) {
                type = Items.SHIP_BP;
            }
        }

        for (IndustrySpecAPI i : Global.getSettings().getAllIndustrySpecs()) {
            if (Objects.equals(i.getId(), specId)) {
                type = Items.INDUSTRY_BP;
            }
        }

        for (HullModSpecAPI h : Global.getSettings().getAllHullModSpecs()) {
            if (Objects.equals(h.getId(), specId)) {
                type = Items.MODSPEC;
            }
        }

        return type;
    }
}
