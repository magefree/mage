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
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author notgreat
 */
public final class FangsOfKalonia extends CardImpl {

    public FangsOfKalonia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Put a +1/+1 counter on target creature you control, then double the number of +1/+1 counters on each creature that had a +1/+1 counter put on it this way.
        this.getSpellAbility().addEffect(new FangsOfKaloniaEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Overload {4}{G}{G}
        this.addAbility(new OverloadAbility(
                this, new FangsOfKaloniaOverloadEffect(),
                new ManaCostsImpl<>("{4}{G}{G}")
        ));
    }

    private FangsOfKalonia(final FangsOfKalonia card) {
        super(card);
    }

    @Override
    public FangsOfKalonia copy() {
        return new FangsOfKalonia(this);
    }
}

//Based on Spectacular Showdown
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
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.addCounters(CounterType.P1P1.createInstance(), source, game)) {
            return false;
        }
        return permanent.addCounters(CounterType.P1P1.createInstance(
                permanent.getCounters(game).getCount(CounterType.P1P1)), source.getControllerId(), source, game);
    }
}

class FangsOfKaloniaOverloadEffect extends OneShotEffect {

    FangsOfKaloniaOverloadEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on each creature you control, " +
                "then double the number of +1/+1 counters on each creature that had a +1/+1 counter put on it this way";
    }

    private FangsOfKaloniaOverloadEffect(final FangsOfKaloniaOverloadEffect effect) {
        super(effect);
    }

    @Override
    public FangsOfKaloniaOverloadEffect copy() {
        return new FangsOfKaloniaOverloadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(permanent -> permanent.addCounters(
                        CounterType.P1P1.createInstance(), source, game
                ))
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        for (Permanent permanent : permanents){
            permanent.addCounters(CounterType.P1P1.createInstance(
                    permanent.getCounters(game).getCount(CounterType.P1P1)), source.getControllerId(), source, game);
        }
        return true;
    }
}
