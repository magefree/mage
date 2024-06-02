package mage.cards.f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.j256.ormlite.field.types.IntegerObjectType;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.counter.DoubleCounterOnEachPermanentEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
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

/**
 *
 * @author grimreap124
 */
public final class FangsOfKalonia extends CardImpl {

    public FangsOfKalonia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.SORCERY }, "{1}{G}");

        // Put a +1/+1 counter on target creature you control, then double the number of +1/+1 counters on each creature that had a +1/+1 counter put on it this way.
        this.getSpellAbility().addEffect(new FangsOfKaloniaEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Overload {4}{G}{G}
        Ability overloadAbility = new OverloadAbility(this, new FangsOfKaloniaOverloadEffect(),
                new ManaCostsImpl<>("{4}{G}{G}"));
        this.addAbility(overloadAbility);
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
        staticText = "Put a +1/+1 counter on target creature you control, then double the number of +1/+1 counters on each creature that had a +1/+1 counter put on it this way";
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
        UUID targetId = source.getTargets().getFirstTarget();

        if (targetId == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(targetId);
        if (permanent == null) {
            return false;
        }

        int preP1P1Count = permanent.getCounters(game).getCount(CounterType.P1P1);
        new AddCountersTargetEffect(CounterType.P1P1.createInstance()).apply(game, source);
        int postP1P1Count = permanent.getCounters(game).getCount(CounterType.P1P1);
        if (postP1P1Count > preP1P1Count) {
            new DoubleCountersTargetEffect(CounterType.P1P1).apply(game, source);
        }
        return true;
    }
}

class FangsOfKaloniaOverloadEffect extends OneShotEffect {

    FangsOfKaloniaOverloadEffect() {
        super(Outcome.Benefit);
        staticText = "Put a +1/+1 counter on each creature you control, then double the number of +1/+1 counters on each creature that had a +1/+1 counter put on it this way";
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

        List<Permanent> controlledCreatures = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game);
        Map<UUID, Integer> preP1P1Counts = new HashMap<>();

        for (Permanent creature : controlledCreatures) {
            preP1P1Counts.put(creature.getId(), creature.getCounters(game).getCount(CounterType.P1P1));
            creature.addCounters(CounterType.P1P1.createInstance(1), source.getControllerId(), source,
                    game);
        }

        for (Permanent creature : controlledCreatures) {
            int postP1P1Count = creature.getCounters(game).getCount(CounterType.P1P1);
            Integer preP1P1Count = preP1P1Counts.get(creature.getId());
            if (preP1P1Count == null) {
                continue;
            }
            if (postP1P1Count > preP1P1Count) {
                creature.addCounters(CounterType.P1P1.createInstance(postP1P1Count), source.getControllerId(), source,
                        game);
            }
        }

        UUID targetId = source.getTargets().getFirstTarget();

        if (targetId == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(targetId);
        if (permanent == null) {
            return false;
        }

        int preP1P1Count = permanent.getCounters(game).getCount(CounterType.P1P1);
        new AddCountersTargetEffect(CounterType.P1P1.createInstance()).apply(game, source);
        int postP1P1Count = permanent.getCounters(game).getCount(CounterType.P1P1);
        if (postP1P1Count > preP1P1Count) {
            new DoubleCountersTargetEffect(CounterType.P1P1).apply(game, source);
        }
        return true;
    }
}