package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public final class SpellgorgerWeird extends CardImpl {

    public SpellgorgerWeird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.WEIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a noncreature spell, put a +1/+1 counter on Spellgorger Weird.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private SpellgorgerWeird(final SpellgorgerWeird card) {
        super(card);
    }

    @Override
    public SpellgorgerWeird copy() {
        return new SpellgorgerWeird(this);
    }
}
