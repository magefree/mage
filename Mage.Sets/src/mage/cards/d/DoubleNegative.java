
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public final class DoubleNegative extends CardImpl {

    public DoubleNegative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}{R}");




        // Counter up to two target spells.
        Effect effect = new CounterTargetEffect();
        effect.setText("Counter up to two target spells");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetSpell(0, 2, StaticFilters.FILTER_SPELL));
    }

    private DoubleNegative(final DoubleNegative card) {
        super(card);
    }

    @Override
    public DoubleNegative copy() {
        return new DoubleNegative(this);
    }
}
