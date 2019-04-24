
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LevelX2
 */
public final class HullBreach extends CardImpl {

    public HullBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}{G}");

        // Choose one - Destroy target artifact;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Target target = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(target);
        // or destroy target enchantment;
        Mode mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        target = new TargetEnchantmentPermanent();
        mode.getTargets().add(target);
        this.getSpellAbility().addMode(mode);
        // or destroy target artifact and target enchantment.
        mode = new Mode();
        Effect effect = new DestroyTargetEffect(false, true);
        effect.setText("destroy target artifact and target enchantment");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetArtifactPermanent());
        mode.getTargets().add(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);

    }

    public HullBreach(final HullBreach card) {
        super(card);
    }

    @Override
    public HullBreach copy() {
        return new HullBreach(this);
    }
}
