
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;

/**
 *
 * @author Loki
 */
public final class ElvishHandservant extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Giant spell");

    static {
        filter.add(SubType.GIANT.getPredicate());
    }

    public ElvishHandservant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a player casts a Giant spell, you may put a +1/+1 counter on Elvish Handservant.
        this.addAbility(new SpellCastAllTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), filter, true));
    }

    private ElvishHandservant(final ElvishHandservant card) {
        super(card);
    }

    @Override
    public ElvishHandservant copy() {
        return new ElvishHandservant(this);
    }
}
