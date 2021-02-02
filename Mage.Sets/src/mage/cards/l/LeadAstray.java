
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class LeadAstray extends CardImpl {

    public LeadAstray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Tap up to two target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private LeadAstray(final LeadAstray card) {
        super(card);
    }

    @Override
    public LeadAstray copy() {
        return new LeadAstray(this);
    }
}
