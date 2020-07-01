package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.PlanarRollWatcher;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class PlanarDieRollCostIncreasingEffect extends CostModificationEffectImpl {

    private final UUID originalId;

    public PlanarDieRollCostIncreasingEffect(UUID originalId) {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.originalId = originalId;
    }

    private PlanarDieRollCostIncreasingEffect(final PlanarDieRollCostIncreasingEffect effect) {
        super(effect);
        this.originalId = effect.originalId;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        PlanarRollWatcher watcher = game.getState().getWatcher(PlanarRollWatcher.class);
        if (activePlayer == null && watcher == null) {
            return false;
        }

        int rolledCounter = watcher.getNumberTimesPlanarDieRolled(activePlayer.getId());
        CardUtil.increaseCost(abilityToModify, rolledCounter);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getOriginalId().equals(originalId);
    }

    @Override
    public PlanarDieRollCostIncreasingEffect copy() {
        return new PlanarDieRollCostIncreasingEffect(this);
    }
}
