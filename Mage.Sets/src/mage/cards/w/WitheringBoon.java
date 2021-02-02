
package mage.cards.w;

import java.util.UUID;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author markedagain
 */
public final class WitheringBoon extends CardImpl {

    public WitheringBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // As an additional cost to cast Withering Boon, pay 3 life.
        this.getSpellAbility().addCost(new PayLifeCost(3));
        // Counter target creature spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
    }

    private WitheringBoon(final WitheringBoon card) {
        super(card);
    }

    @Override
    public WitheringBoon copy() {
        return new WitheringBoon(this);
    }
}
