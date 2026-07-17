package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author notgreat
 */
public final class FangsOfKalonia extends CardImpl {

    public FangsOfKalonia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Put a +1/+1 counter on target creature you control, then double the number of +1/+1 counters on each creature that had a +1/+1 counter put on it this way.
        // Overload {4}{G}{G}
        OverloadAbility.implementOverloadAbility(this, new ManaCostsImpl<>("{4}{G}{G}"),
                new TargetControlledCreaturePermanent(), new FangsOfKaloniaEffect());
    }

    private FangsOfKalonia(final FangsOfKalonia card) {
        super(card);
    }

    @Override
    public FangsOfKalonia copy() {
        return new FangsOfKalonia(this);
    }
}
class FangsOfKaloniaEffect extends OneShotEffect {

    FangsOfKaloniaEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on target creature you control, " +
                "then double the number of +1/+1 counters on each creature that had a +1/+1 counter put on it this way";
    }

    private FangsOfKaloniaEffect(final FangsOfKaloniaEffect effect) {
        super(effect);
    }

    @Override
    public FangsOfKaloniaEffect copy() {
        return new FangsOfKaloniaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = getTargetPointer().getTargets(game, source)
                .stream().map(game::getPermanent).filter(Objects::nonNull)
                .filter(permanent -> permanent.addCounters(
                        CounterType.P1P1.createInstance(), source, game
                ))
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        for (Permanent permanent : permanents) {
            permanent.addCounters(CounterType.P1P1.createInstance(
                    permanent.getCounters(game).getCount(CounterType.P1P1)), source.getControllerId(), source, game);
        }
        return true;
    }
}
