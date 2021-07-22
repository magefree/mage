
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 *
 * @author Backfir3
 */
public final class Kurgadon extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a creature spell with mana value 6 or greater");

    static {
        filterSpell.add(CardType.CREATURE.getPredicate());
		filterSpell.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 5));
    }

    public Kurgadon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

		// Whenever you cast a creature spell with converted mana cost 6 or greater, put three +1/+1 counters on Kurgadon.
        SpellCastControllerTriggeredAbility ability = new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), filterSpell, false);
        this.addAbility(ability);
    }

    private Kurgadon(final Kurgadon card) {
        super(card);
    }

    @Override
    public Kurgadon copy() {
        return new Kurgadon(this);
    }
}
