package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SpinalCentipede extends CardImpl {

    public SpinalCentipede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Spinal Centipede dies, put a +1/+1 counter on target creature you control.
        Ability ability = new DiesSourceTriggeredAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()
        ), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SpinalCentipede(final SpinalCentipede card) {
        super(card);
    }

    @Override
    public SpinalCentipede copy() {
        return new SpinalCentipede(this);
    }
}
