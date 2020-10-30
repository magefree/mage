package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KinsbaileCourier extends CardImpl {

    public KinsbaileCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Kinsbaile Courier enters the battlefield, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Encore {2}{W}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{2}{W}")));
    }

    private KinsbaileCourier(final KinsbaileCourier card) {
        super(card);
    }

    @Override
    public KinsbaileCourier copy() {
        return new KinsbaileCourier(this);
    }
}
