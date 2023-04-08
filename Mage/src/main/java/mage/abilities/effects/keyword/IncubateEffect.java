package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.IncubatorToken;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class IncubateEffect extends OneShotEffect {

    private final int amount;

    public IncubateEffect(int amount) {
        super(Outcome.Detriment);
        this.amount = amount;
        staticText = "incubate " + amount + " <i>(Create an Incubator artifact token with " +
                CardUtil.numberToText(amount, "a") + " +1/+1 counter" + (amount > 1 ? "s" : "") +
                " on it and \"{2}: Transform this artifact.\" It transforms into a 0/0 Phyrexian artifact creature.)</i>";
    }

    public IncubateEffect(final IncubateEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public IncubateEffect copy() {
        return new IncubateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new IncubatorToken();
        token.putOntoBattlefield(1, game, source);
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent == null) {
                continue;
            }
            permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
        }
        return true;
    }
}
