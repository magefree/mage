package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.AdventurePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Wandermare extends CardImpl {

    private static final FilterSpell filter
            = new FilterCreatureSpell("a creature spell that has an Adventure");

    static {
        filter.add(AdventurePredicate.instance);
    }

    public Wandermare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a creature spell that has an Adventure, put a +1/+1 counter on Wandermare.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false
        ));
    }

    private Wandermare(final Wandermare card) {
        super(card);
    }

    @Override
    public Wandermare copy() {
        return new Wandermare(this);
    }
}
