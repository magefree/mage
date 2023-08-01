package mage.abilities.effects.common.turn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.util.CardUtil;

/**
 *
 * @author Mael
 */
public class SkipNextTurnSourceEffect extends OneShotEffect {

    int numberOfTurns;

    public SkipNextTurnSourceEffect() {
        this(1);
    }

    public SkipNextTurnSourceEffect(int numberOfTurns) {
        super(Outcome.Neutral);
        this.numberOfTurns = numberOfTurns;
        staticText = "you skip your next " + (numberOfTurns == 1 ? "turn" : CardUtil.numberToText(numberOfTurns) + " turns");
    }

    public SkipNextTurnSourceEffect(final SkipNextTurnSourceEffect effect) {
        super(effect);
        this.numberOfTurns = effect.numberOfTurns;
    }

    @Override
    public SkipNextTurnSourceEffect copy() {
        return new SkipNextTurnSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = null;
        if (source instanceof ActivatedAbilityImpl) {
            playerId = ((ActivatedAbilityImpl) source).getActivatorId();
        }
        if (playerId == null) {
            playerId = source.getControllerId();
        }
        for (int i = 0; i < numberOfTurns; i++) {
            game.getState().getTurnMods().add(new TurnMod(playerId).withSkipTurn());
        }
        return true;
    }
}
