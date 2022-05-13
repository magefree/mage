
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public final class MoldAdder extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a blue or black spell");
    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.BLACK)));
    }

    public MoldAdder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an opponent casts a blue or black spell, you may put a +1/+1 counter on Mold Adder.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, true));
    }

    private MoldAdder(final MoldAdder card) {
        super(card);
    }

    @Override
    public MoldAdder copy() {
        return new MoldAdder(this);
    }
}
