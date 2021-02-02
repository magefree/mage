
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.RippleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class SurgingAether extends CardImpl {

    public SurgingAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Ripple 4
        this.addAbility(new RippleAbility(4).setRuleAtTheTop(true));

        // Return target permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private SurgingAether(final SurgingAether card) {
        super(card);
    }

    @Override
    public SurgingAether copy() {
        return new SurgingAether(this);
    }
}
