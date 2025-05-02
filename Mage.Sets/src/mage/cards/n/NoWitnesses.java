package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class NoWitnesses extends CardImpl {

    public NoWitnesses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Each player who controls the most creatures investigates. Then destroy all creatures.
        this.getSpellAbility().addEffect(new NoWitnessesEffect());
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES).concatBy("Then"));
    }

    private NoWitnesses(final NoWitnesses card) {
        super(card);
    }

    @Override
    public NoWitnesses copy() {
        return new NoWitnesses(this);
    }
}

class NoWitnessesEffect extends OneShotEffect {

    NoWitnessesEffect() {
        super(Outcome.Benefit);
        staticText = "each player who controls the most creatures investigates";
    }

    private NoWitnessesEffect(final NoWitnessesEffect effect) {
        super(effect);
    }

    @Override
    public NoWitnessesEffect copy() {
        return new NoWitnessesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> map = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .collect(Collectors.toMap(Function.identity(), x -> 1, Integer::sum));
        if (map.isEmpty()) {
            return false;
        }
        int maxValue = map
                .values()
                .stream()
                .mapToInt(x -> x)
                .max()
                .orElse(0);
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (map.getOrDefault(playerId, -1) >= maxValue) {
                InvestigateEffect.doInvestigate(playerId, 1, game, source);
            }
        }
        return true;
    }
}
