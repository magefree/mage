package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 *
 * @author weirddan455
 */
public final class AscendantPackleader extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a permanent with mana value 4 or greater");
    private static final FilterSpell filter2 = new FilterSpell("a spell with mana value 4 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
        filter2.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    public AscendantPackleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Ascendant Packleader enters the battlefield with a +1/+1 counter on it if you control a permanent with mana value 4 or greater.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 0, true),
                null,
                "with a +1/+1 counter on it if you control a permanent with mana value 4 or greater"
        ));

        // Whenever you cast a spell with mana value 4 or greater, put a +1/+1 counter on Ascendant Packleader.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                filter2,
                false
        ));
    }

    private AscendantPackleader(final AscendantPackleader card) {
        super(card);
    }

    @Override
    public AscendantPackleader copy() {
        return new AscendantPackleader(this);
    }
}
