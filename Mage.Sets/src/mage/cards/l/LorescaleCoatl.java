package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class LorescaleCoatl extends CardImpl {

    public LorescaleCoatl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new DrawCardControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));
    }

    private LorescaleCoatl(final LorescaleCoatl card) {
        super(card);
    }

    @Override
    public LorescaleCoatl copy() {
        return new LorescaleCoatl(this);
    }
}
