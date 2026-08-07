package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LakeshoreApothecary extends CardImpl {

    public LakeshoreApothecary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you draw your second card each turn, put a +1/+1 counter on this creature.
        this.addAbility(new DrawNthCardTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, 2));
    }

    private LakeshoreApothecary(final LakeshoreApothecary card) {
        super(card);
    }

    @Override
    public LakeshoreApothecary copy() {
        return new LakeshoreApothecary(this);
    }
}
