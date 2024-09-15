package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class NecropolisRegent extends CardImpl {

    public NecropolisRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature you control deals combat damage to a player, put that many +1/+1 counters on it.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), SavedDamageValue.MANY)
                        .setText("put that many +1/+1 counters on it"),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.PERMANENT, true
        ));
    }

    private NecropolisRegent(final NecropolisRegent card) {
        super(card);
    }

    @Override
    public NecropolisRegent copy() {
        return new NecropolisRegent(this);
    }
}
