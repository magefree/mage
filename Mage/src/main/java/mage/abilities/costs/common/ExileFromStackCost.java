
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class ExileFromStackCost extends CostImpl {

    public ExileFromStackCost(TargetSpell target) {
        this.addTarget(target);
        this.text = "Exile " + target.getTargetName();
    }

    public ExileFromStackCost(ExileFromStackCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        if (targets.choose(Outcome.Exile, controllerId, sourceId, game)) {
            Player player = game.getPlayer(controllerId);
            for (UUID targetId : targets.get(0).getTargets()) {
                Spell spellToExile = game.getStack().getSpell(targetId);
                if (spellToExile == null) {
                    return false;
                }
                String spellName = spellToExile.getName();
                if (spellToExile.isCopy()) {
                    game.getStack().remove(spellToExile, game);
                } else {
                    spellToExile.moveToExile(null, "", ability.getSourceId(), game);
                }
                paid = true;
                if (!game.isSimulation()) {
                    game.informPlayers(player.getLogName() + " exiles " + spellName + " (as costs)");
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public ExileFromStackCost copy() {
        return new ExileFromStackCost(this);
    }

}
