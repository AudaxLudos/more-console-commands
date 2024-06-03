package moreconsolecommands.commands;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;
import com.fs.starfarer.api.loading.IndustrySpecAPI;
import com.fs.starfarer.api.loading.WeaponSpecAPI;
import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.Console;

import java.util.Objects;

public class AddBlueprint implements BaseCommand {
    @Override
    public CommandResult runCommand(String args, CommandContext context) {
        if (!context.isCampaignAccessible()) {
            Console.showMessage("Error: This command is campaign-only.");
            return BaseCommand.CommandResult.WRONG_CONTEXT;
        }

        if (args.isEmpty()) {
            return CommandResult.BAD_SYNTAX;
        }

        Object spec = getBlueprintSpec(args);

        if (spec == null) {
            Console.showMessage("No blueprint found with id: " + args);
            return CommandResult.ERROR;
        }

        if (isSpecKnownByPlayer(spec)) {
            Console.showMessage(args + " Blueprint is known by player");
            return CommandResult.ERROR;
        }

        learnBlueprint(spec);

        Console.showMessage("Player learned " + args + " blueprint");
        return BaseCommand.CommandResult.SUCCESS;
    }

    public Object getBlueprintSpec(String specId) {
        Object spec = null;
        for (FighterWingSpecAPI f : Global.getSettings().getAllFighterWingSpecs()) {
            if (Objects.equals(f.getId(), specId)) {
                spec = f;
            }
        }

        for (WeaponSpecAPI w : Global.getSettings().getAllWeaponSpecs()) {
            if (Objects.equals(w.getWeaponId(), specId)) {
                spec = w;
            }
        }

        for (ShipHullSpecAPI s : Global.getSettings().getAllShipHullSpecs()) {
            if (Objects.equals(s.getHullId(), specId)) {
                spec = s;
            }
        }

        for (IndustrySpecAPI i : Global.getSettings().getAllIndustrySpecs()) {
            if (Objects.equals(i.getId(), specId)) {
                spec = i;
            }
        }

        for (HullModSpecAPI h : Global.getSettings().getAllHullModSpecs()) {
            if (Objects.equals(h.getId(), specId)) {
                spec = h;
            }
        }

        return spec;
    }

    public boolean isSpecKnownByPlayer(Object spec) {
        boolean knownToPlayer = false;
        FactionAPI playerFaction = Global.getSector().getPlayerFaction();

        if (spec instanceof FighterWingSpecAPI) {
            knownToPlayer = playerFaction.knowsFighter(((FighterWingSpecAPI) spec).getId());
        } else if (spec instanceof WeaponSpecAPI) {
            knownToPlayer = playerFaction.knowsWeapon(((WeaponSpecAPI) spec).getWeaponId());
        } else if (spec instanceof ShipHullSpecAPI) {
            knownToPlayer = playerFaction.knowsShip(((ShipHullSpecAPI) spec).getHullId());
        } else if (spec instanceof IndustrySpecAPI) {
            knownToPlayer = playerFaction.knowsIndustry(((IndustrySpecAPI) spec).getId());
        } else if (spec instanceof HullModSpecAPI) {
            knownToPlayer = playerFaction.knowsIndustry(((HullModSpecAPI) spec).getId());
        }

        return knownToPlayer;
    }

    public void learnBlueprint(Object spec) {
        FactionAPI playerFaction = Global.getSector().getPlayerFaction();

        if (spec instanceof FighterWingSpecAPI) {
            playerFaction.addKnownFighter(((FighterWingSpecAPI) spec).getId(), true);
        } else if (spec instanceof WeaponSpecAPI) {
            playerFaction.addKnownWeapon(((WeaponSpecAPI) spec).getWeaponId(), true);
        } else if (spec instanceof ShipHullSpecAPI) {
            playerFaction.addKnownShip(((ShipHullSpecAPI) spec).getHullId(), true);
        } else if (spec instanceof IndustrySpecAPI) {
            playerFaction.addKnownIndustry(((IndustrySpecAPI) spec).getId());
        } else if (spec instanceof HullModSpecAPI) {
            playerFaction.addKnownHullMod(((HullModSpecAPI) spec).getId());
        }
    }
}
