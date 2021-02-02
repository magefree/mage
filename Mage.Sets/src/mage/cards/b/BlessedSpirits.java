
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;

/**
 *
 * @author fireshoes
 */
public final class BlessedSpirits extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("an enchantment spell");
    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public BlessedSpirits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever you cast an enchantment spell, put a +1/+1 counter on Blessed Spirits.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false));
    }

    private BlessedSpirits(final BlessedSpirits card) {
        super(card);
    }

    @Override
    public BlessedSpirits copy() {
        return new BlessedSpirits(this);
    }
}
