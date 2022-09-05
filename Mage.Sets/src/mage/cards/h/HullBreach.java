package mage.cards.h;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class HullBreach extends CardImpl {

    public HullBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}{G}");

        // Choose one - Destroy target artifact;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        // or destroy target enchantment;
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);
        // or destroy target artifact and target enchantment.
        mode = new Mode(new DestroyTargetEffect().setTargetPointer(new EachTargetPointer()));
        mode.addTarget(new TargetArtifactPermanent());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private HullBreach(final HullBreach card) {
        super(card);
    }

    @Override
    public HullBreach copy() {
        return new HullBreach(this);
    }
}
