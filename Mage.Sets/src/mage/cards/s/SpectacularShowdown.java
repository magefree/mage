package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SpectacularShowdown extends CardImpl {

    public SpectacularShowdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Put a double strike counter on target creature, then goad each creature that had a double strike counter put on it this way.
        // Overload {4}{R}{R}{R}
        OverloadAbility.implementOverloadAbility(this, new ManaCostsImpl<>("{4}{R}{R}{R}"),
                new TargetCreaturePermanent(), new SpectacularShowdownEffect());
    }

    private SpectacularShowdown(final SpectacularShowdown card) {
        super(card);
    }

    @Override
    public SpectacularShowdown copy() {
        return new SpectacularShowdown(this);
    }
}

class SpectacularShowdownEffect extends OneShotEffect {

    SpectacularShowdownEffect() {
        super(Outcome.Benefit);
        staticText = "put a double strike counter on target creature, " +
                "then goad each creature that had a double strike counter put on it this way";
    }

    private SpectacularShowdownEffect(final SpectacularShowdownEffect effect) {
        super(effect);
    }

    @Override
    public SpectacularShowdownEffect copy() {
        return new SpectacularShowdownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = getTargetPointer().getTargets(game, source)
                .stream().map(game::getPermanent).filter(Objects::nonNull)
                .filter(permanent -> permanent.addCounters(
                        CounterType.DOUBLE_STRIKE.createInstance(), source, game
                ))
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        game.addEffect(new GoadTargetEffect().setTargetPointer(new FixedTargets(permanents, game)), source);
        return true;
    }
}
