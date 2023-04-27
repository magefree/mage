package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.StormCrowToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class AttemptedMurder extends CardImpl {

    public AttemptedMurder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Choose target creature. Roll X six-sided dice. For each even result, put two -1/-1 counters on that creature. For each odd result, create a 1/2 blue Bird creature token with flying named Storm Crow.
        this.getSpellAbility().addEffect(new AttemptedMurderEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AttemptedMurder(final AttemptedMurder card) {
        super(card);
    }

    @Override
    public AttemptedMurder copy() {
        return new AttemptedMurder(this);
    }
}

class AttemptedMurderEffect extends OneShotEffect {

    AttemptedMurderEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature. Roll X six-sided dice. For each even result, " +
                "put two -1/-1 counters on that creature. For each odd result, " +
                "create a 1/2 blue Bird creature token with flying named Storm Crow";
    }

    private AttemptedMurderEffect(final AttemptedMurderEffect effect) {
        super(effect);
    }

    @Override
    public AttemptedMurderEffect copy() {
        return new AttemptedMurderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int xValue = source.getManaCostsToPay().getX();
        if (player == null || xValue < 1) {
            return false;
        }
        Map<Integer, Integer> rollMap = player
                .rollDice(outcome, source, game, 6, xValue, 0)
                .stream()
                .map(x -> x % 2)
                .collect(Collectors.toMap(Function.identity(), x -> 1, Integer::sum));
        int evens = rollMap.getOrDefault(0, 0);
        if (evens > 0) {
            Optional.ofNullable(game.getPermanent(getTargetPointer().getFirst(game, source)))
                    .ifPresent(permanent -> permanent.addCounters(CounterType.M1M1.createInstance(2 * evens), source, game));
        }
        int odds = rollMap.getOrDefault(1, 0);
        if (odds > 0) {
            new StormCrowToken().putOntoBattlefield(odds, game, source);
        }
        return true;
    }
}
