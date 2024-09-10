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
        staticText = "incubate " + amount + ". <i>(Create an Incubator artifact token with " +
                CardUtil.getOneOneCountersText(amount) +
                " on it and \"{2}: Transform this artifact.\" It transforms into a 0/0 Phyrexian artifact creature.)</i>";
    }

    protected IncubateEffect(final IncubateEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public IncubateEffect copy() {
        return new IncubateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return doIncubate(amount, game, source);
    }

    public static boolean doIncubate(int amount, Game game, Ability source) {
        return doIncubate(amount, source.getControllerId(), game, source);
    }

    public static boolean doIncubate(int amount, UUID playerId, Game game, Ability source) {
        Token token = new IncubatorToken();
        token.putOntoBattlefield(1, game, source, playerId);
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent != null && amount > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}
