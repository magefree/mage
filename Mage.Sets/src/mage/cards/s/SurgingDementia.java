
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.RippleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author klayhamn
 */
public final class SurgingDementia extends CardImpl {

    public SurgingDementia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Ripple 4
        this.addAbility(new RippleAbility(4).setRuleAtTheTop(true));

        // Target player discards a card.
        this.getSpellAbility().getEffects().add(new DiscardTargetEffect(1));
        this.getSpellAbility().getTargets().add(new TargetPlayer());
    }

    private SurgingDementia(final SurgingDementia card) {
        super(card);
    }

    @Override
    public SurgingDementia copy() {
        return new SurgingDementia(this);
    }
}
