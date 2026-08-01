package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class RavenhillFlock extends CardImpl {

    public RavenhillFlock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw a card, put a +1/+1 counter on this creature.
        this.addAbility(new DrawCardControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));
    }

    private RavenhillFlock(final RavenhillFlock card) {
        super(card);
    }

    @Override
    public RavenhillFlock copy() {
        return new RavenhillFlock(this);
    }
}
