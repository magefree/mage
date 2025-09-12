package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntrepidTenderfoot extends CardImpl {

    public IntrepidTenderfoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}: Put a +1/+1 counter on this creature. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(3)
        ));
    }

    private IntrepidTenderfoot(final IntrepidTenderfoot card) {
        super(card);
    }

    @Override
    public IntrepidTenderfoot copy() {
        return new IntrepidTenderfoot(this);
    }
}
