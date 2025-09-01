package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.AllyToken;
import mage.game.permanent.token.Token;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MatchTheOdds extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Creatures your opponents control",
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE)
    );

    public MatchTheOdds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        this.subtype.add(SubType.LESSON);

        // Create a 1/1 white Ally creature token. Put a +1/+1 counter on it for each creature your opponents control.
        this.getSpellAbility().addEffect(new MatchTheOddsEffect());
        this.getSpellAbility().addHint(hint);
    }

    private MatchTheOdds(final MatchTheOdds card) {
        super(card);
    }

    @Override
    public MatchTheOdds copy() {
        return new MatchTheOdds(this);
    }
}

class MatchTheOddsEffect extends OneShotEffect {

    MatchTheOddsEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 white Ally creature token. " +
                "Put a +1/+1 counter on it for each creature your opponents control";
    }

    private MatchTheOddsEffect(final MatchTheOddsEffect effect) {
        super(effect);
    }

    @Override
    public MatchTheOddsEffect copy() {
        return new MatchTheOddsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new AllyToken();
        token.putOntoBattlefield(1, game, source);
        int count = game
                .getBattlefield()
                .count(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, source.getControllerId(), source, game);
        if (count < 1) {
            return true;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Optional.ofNullable(tokenId)
                    .map(game::getPermanent)
                    .ifPresent(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(count), source, game));
        }
        return true;
    }
}
