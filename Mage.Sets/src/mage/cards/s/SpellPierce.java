

package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class SpellPierce extends CardImpl {

    public SpellPierce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Counter target noncreature spell unless its controller pays 2.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));
    }

    private SpellPierce(final SpellPierce card) {
        super(card);
    }

    @Override
    public SpellPierce copy() {
        return new SpellPierce(this);
    }
}
