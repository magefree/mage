package mage.cards.c;

import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConcertedDefense extends CardImpl {

    private static final DynamicValue xValue = new AdditiveDynamicValue(PartyCount.instance, StaticValue.get(1));

    public ConcertedDefense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target noncreature spell unless its controller pays {1} plus an additional {1} for each creature in your party.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(xValue).setText(
                "Counter target noncreature spell unless its controller pays {1} " +
                        "plus an additional {1} for each creature in your party. " + PartyCount.getReminder()
        ));
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.getSpellAbility().addHint(PartyCountHint.instance);
    }

    private ConcertedDefense(final ConcertedDefense card) {
        super(card);
    }

    @Override
    public ConcertedDefense copy() {
        return new ConcertedDefense(this);
    }
}
