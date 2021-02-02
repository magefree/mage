
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyMultiTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;
/**
 *
 * @author vereena42
 */
public final class StompAndHowl extends CardImpl {

    public StompAndHowl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");
        
        // Destroy target artifact and target enchantment.
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addEffect(new DestroyMultiTargetEffect());
    }

    private StompAndHowl(final StompAndHowl card) {
        super(card);
    }

    @Override
    public StompAndHowl copy() {
        return new StompAndHowl(this);
    }
}
