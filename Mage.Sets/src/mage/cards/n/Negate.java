

package mage.cards.n;

import java.util.UUID;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class Negate extends CardImpl {

    public Negate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    private Negate(final Negate card) {
        super(card);
    }

    @Override
    public Negate copy() {
        return new Negate(this);
    }

}
