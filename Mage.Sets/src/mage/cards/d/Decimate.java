
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author shieldal
 */
public final class Decimate extends CardImpl {

    public Decimate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{G}");

        // Destroy target artifact, target creature, target enchantment, and target land.
        Effect effect = new DestroyTargetEffect(false, true);
        effect.setText("Destroy target artifact, target creature, target enchantment, and target land.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private Decimate(final Decimate card) {
        super(card);
    }

    @Override
    public Decimate copy() {
        return new Decimate(this);
    }
}
