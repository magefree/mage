package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class RollPlanarDieSpecialAction extends SpecialAction {

    public RollPlanarDieSpecialAction(UUID playerId) {
        super();
        this.addEffect(new RollPlanarDieSpecialActionEffect());
        this.setControllerId(playerId);
        this.setCostAdjuster((ability, game) -> {
            int count = ((SpecialAction) ability).getActivatedThisTurnCount(game);
            ability.getCosts().clear();
            if (count > 0) {
                ability.addCost(new GenericManaCost(count));
            }
        });
    }

    private RollPlanarDieSpecialAction(final RollPlanarDieSpecialAction action) {
        super(action);
    }

    @Override
    public RollPlanarDieSpecialAction copy() {
        return new RollPlanarDieSpecialAction(this);
    }

    @Override
    public String getRule() {
        return "this is the rule";
    }
}

class RollPlanarDieSpecialActionEffect extends OneShotEffect {

    RollPlanarDieSpecialActionEffect() {
        super(Outcome.Benefit);
    }

    private RollPlanarDieSpecialActionEffect(final RollPlanarDieSpecialActionEffect effect) {
        super(effect);
    }

    @Override
    public RollPlanarDieSpecialActionEffect copy() {
        return new RollPlanarDieSpecialActionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.rollPlanarDieResult(outcome, source, game);
        return true;
    }
}
