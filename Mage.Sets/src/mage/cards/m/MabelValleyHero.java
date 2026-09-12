package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MabelValleyHero extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that entered this turn");

    static {
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public MabelValleyHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Mabel or another creature you control enters, put a +1/+1 counter on target creature that entered this turn.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
            StaticFilters.FILTER_PERMANENT_CREATURE, false, true
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MabelValleyHero(final MabelValleyHero card) {
        super(card);
    }

    @Override
    public MabelValleyHero copy() {
        return new MabelValleyHero(this);
    }
}
