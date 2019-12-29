package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodthirstyAerialist extends CardImpl {

    public BloodthirstyAerialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you gain life, put a +1/+1 counter on Bloodthirsty Aerialist.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));
    }

    private BloodthirstyAerialist(final BloodthirstyAerialist card) {
        super(card);
    }

    @Override
    public BloodthirstyAerialist copy() {
        return new BloodthirstyAerialist(this);
    }
}
