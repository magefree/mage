

package mage.cards.u;

import java.util.UUID;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 * @author JRHerlehy
 *         Created on 4/7/18.
 */
public final class Unwind extends CardImpl {

    public Unwind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Counter target noncreature spell. Untap up to three lands.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new UntapLandsEffect(3));
    }

    private Unwind(final Unwind card) {
        super(card);
    }

    @Override
    public Unwind copy() {
        return new Unwind(this);
    }
}
