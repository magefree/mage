
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureSpell;
import mage.target.TargetSpell;

/**
 *
 * @author Jgod
 */
public final class PreemptiveStrike extends CardImpl {

    public PreemptiveStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Counter target creature spell.
        this.getSpellAbility().addTarget(new TargetSpell(new FilterCreatureSpell()));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    private PreemptiveStrike(final PreemptiveStrike card) {
        super(card);
    }

    @Override
    public PreemptiveStrike copy() {
        return new PreemptiveStrike(this);
    }
}
