package mage.cards.t;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.AdventureCard;
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
public final class TempestHart extends AdventureCard {

    private static final FilterSpell filter = new FilterSpell("a spell with mana value 5 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 4));
    }

    public TempestHart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL, SubType.ELK}, "{3}{G}",
                "Scan the Clouds",
                new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Tempest Hart
        this.getLeftHalfCard().setPT(3, 4);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever you cast a spell with mana value 5 or greater, put a +1/+1 counter on Tempest Hart.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false
        ));

        // Scan the Clouds
        // Draw two cards, then discard two cards.
        this.getRightHalfCard().getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 2));

        finalizeCard();
    }

    private TempestHart(final TempestHart card) {
        super(card);
    }

    @Override
    public TempestHart copy() {
        return new TempestHart(this);
    }
}
