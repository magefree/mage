package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class JuniperOrderRanger extends CardImpl {

    public JuniperOrderRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever another creature enters the battlefield under your control, put a +1/+1 counter on that creature and a +1/+1 counter on Juniper Order Ranger.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_ANOTHER_CREATURE, false, SetTargetPointer.PERMANENT
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .setText("and a +1/+1 counter on {this}"));
        this.addAbility(ability);
    }

    private JuniperOrderRanger(final JuniperOrderRanger card) {
        super(card);
    }

    @Override
    public JuniperOrderRanger copy() {
        return new JuniperOrderRanger(this);
    }
}
