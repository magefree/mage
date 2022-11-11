package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThunderwolfCavalry extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("other creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ThunderwolfCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Crushing Teeth -- Whenever Thunderwolf Cavalry deals combat damage to a player, put a +1/+1 counter on each other creature you control.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false
        ).withFlavorWord("Crushing Teeth"));
    }

    private ThunderwolfCavalry(final ThunderwolfCavalry card) {
        super(card);
    }

    @Override
    public ThunderwolfCavalry copy() {
        return new ThunderwolfCavalry(this);
    }
}
