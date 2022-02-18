package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class CoilingStalker extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("creature you control that doesn't have a +1/+1 counter on it");

    static {
        filter.add(CoilingStalkerPredicate.instance);
    }

    public CoilingStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Ninjutsu {1}{G}
        this.addAbility(new NinjutsuAbility("{1}{G}"));

        // Whenever Coiling Stalker deals combat damage to a player, put a +1/+1 counter on target creature you control that doesn't have a +1/+1 counter on it.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private CoilingStalker(final CoilingStalker card) {
        super(card);
    }

    @Override
    public CoilingStalker copy() {
        return new CoilingStalker(this);
    }
}

enum CoilingStalkerPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getCounters(game).getCount(CounterType.P1P1) == 0;
    }

    @Override
    public String toString() {
        return "doesn't have a +1/+1 counter on it";
    }
}
