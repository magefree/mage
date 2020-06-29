package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenerableKnight extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.KNIGHT);

    public VenerableKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Venerable Knight dies, put a +1/+1 counter on target Knight you control.
        Ability ability = new DiesSourceTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private VenerableKnight(final VenerableKnight card) {
        super(card);
    }

    @Override
    public VenerableKnight copy() {
        return new VenerableKnight(this);
    }
}
