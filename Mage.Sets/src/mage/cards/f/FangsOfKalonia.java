package mage.cards.f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        this.addAbility(
                new OverloadAbility(this, new FangsOfKaloniaOverloadEffect(), new ManaCostsImpl<>("{4}{G}{G}")));
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
    public boolean apply(Game game, Ability source) {
        UUID target = source.getTargets().getFirstTarget();
        Permanent preP1P1Creature = game.getPermanent(target);
        if (preP1P1Creature != null) {
            // Put a +1/+1 counter on target creature you control
            int preP1P1Count = preP1P1Creature.getCounters(game).getCount(CounterType.P1P1);
            preP1P1Creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            game.applyEffects();
            // then double the number of +1/+1 counters on each creature that had a +1/+1 counter put on it this way
            Permanent postP1P1Creature = game.getPermanent(target);
            if (postP1P1Creature != null) {
                int postP1P1Count = postP1P1Creature.getCounters(game).getCount(CounterType.P1P1);
                if (postP1P1Count > preP1P1Count) {

                    int doubled = (postP1P1Count - preP1P1Count);
                    game.informPlayers("post: " + postP1P1Count +
                            " pre: " + preP1P1Count + "adding " + doubled + " +1/+1 counters to "
                            + postP1P1Creature.getName());
                    postP1P1Creature.addCounters(CounterType.P1P1.createInstance(doubled), source.getControllerId(),
                            source, game);
                }
            }
        }

        return true;
    }

    @Override
    public FangsOfKaloniaEffect copy() {
        return new FangsOfKaloniaEffect(this);
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
    public boolean apply(Game game, Ability source) {
        List<Permanent> preP1P1Creatures = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game);
        Map<UUID, Integer> preP1P1Counts = new HashMap<>();

        for (Permanent creature : preP1P1Creatures) {
            preP1P1Counts.put(creature.getId(), creature.getCounters(game).getCount(CounterType.P1P1));
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        List<Permanent> postP1P1Creatures = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game);

        for (Permanent creature : postP1P1Creatures) {

            if (postP1P1Creatures != null) {
                int preP1P1Count = preP1P1Counts.get(creature.getId());
                int postP1P1Count = creature.getCounters(game).getCount(CounterType.P1P1);
                if (postP1P1Count > preP1P1Count) {
                    int doubled = (postP1P1Count - preP1P1Count);
                    creature.addCounters(CounterType.P1P1.createInstance(doubled), source.getControllerId(), source,
                            game);
                }
            }
        }
        return true;
    }

    @Override
    public FangsOfKaloniaOverloadEffect copy() {
        return new FangsOfKaloniaOverloadEffect(this);
    }
}