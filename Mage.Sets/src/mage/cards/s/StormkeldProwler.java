package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormkeldProwler extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with mana value 5 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 4));
    }

    public StormkeldProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell with mana value 5 or greater, put two +1/+1 counters on Stormkeld Prowler.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), filter, false
        ));
    }

    private StormkeldProwler(final StormkeldProwler card) {
        super(card);
    }

    @Override
    public StormkeldProwler copy() {
        return new StormkeldProwler(this);
    }
}
