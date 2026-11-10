package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class BeverlyCrusherShipsDoctor extends CardImpl {

    public BeverlyCrusherShipsDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Seek out -- Whenever you draw your second card each turn, put a +1/+1 counter on each of up to three target creatures.
        Ability ability = new DrawNthCardTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()))
            .withFlavorWord("Seek out");
        ability.addTarget(new TargetCreaturePermanent(0, 3));
        this.addAbility(ability);
    }

    private BeverlyCrusherShipsDoctor(final BeverlyCrusherShipsDoctor card) {
        super(card);
    }

    @Override
    public BeverlyCrusherShipsDoctor copy() {
        return new BeverlyCrusherShipsDoctor(this);
    }
}
