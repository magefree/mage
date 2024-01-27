package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;

/**
 * @author xenohedron
 */
public class LoseLifeOpponentsYouGainLifeLostEffect extends OneShotEffect {

    private final DynamicValue amount;

    public LoseLifeOpponentsYouGainLifeLostEffect(int amount) {
        this(StaticValue.get(amount), amount + " life");
    }

    public LoseLifeOpponentsYouGainLifeLostEffect(DynamicValue amount, String amountLifeText) {
        super(Outcome.GainLife);
        this.amount = amount;
        staticText = "each opponent loses " + amountLifeText + ". You gain life equal to the life lost this way";
    }

    protected LoseLifeOpponentsYouGainLifeLostEffect(final LoseLifeOpponentsYouGainLifeLostEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int lifeToLose = amount.calculate(game, source, this);
        if (lifeToLose < 1) {
            return true;
        }
        int totalLifeLost = game
                .getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(opponent -> opponent.loseLife(lifeToLose, game, source, false))
                .sum();
        if (totalLifeLost > 1) {
            controller.gainLife(totalLifeLost, game, source);
        }
        return true;
    }

    @Override
    public LoseLifeOpponentsYouGainLifeLostEffect copy() {
        return new LoseLifeOpponentsYouGainLifeLostEffect(this);
    }

}
