package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LeatherheadIronGator extends CardImpl {

    public LeatherheadIronGator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CROCODILE);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Leatherhead attacks, put two +1/+1 counters on each creature you control.
        this.addAbility(new AttacksTriggeredAbility(
            new AddCountersAllEffect(
                CounterType.P1P1.createInstance(2), StaticFilters.FILTER_CONTROLLED_CREATURE
            )
        ));
    }

    private LeatherheadIronGator(final LeatherheadIronGator card) {
        super(card);
    }

    @Override
    public LeatherheadIronGator copy() {
        return new LeatherheadIronGator(this);
    }
}
