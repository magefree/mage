package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;
import mage.abilities.common.DiesSourceTriggeredAbility;

/**
 * @author TheElk801
 */
public final class GuulDrazMucklord extends CardImpl {

    public GuulDrazMucklord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Guul Draz Mucklord dies, put a +1/+1 counter on target creature you control.
        Ability ability = new DiesSourceTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private GuulDrazMucklord(final GuulDrazMucklord card) {
        super(card);
    }

    @Override
    public GuulDrazMucklord copy() {
        return new GuulDrazMucklord(this);
    }
}
