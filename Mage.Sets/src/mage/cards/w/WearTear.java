package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LevelX2
 */
public final class WearTear extends SplitCard {

    public WearTear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}", "{W}", SpellAbilityType.SPLIT_FUSED);

        // Wear
        // Destroy target artifact.
        getLeftHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        Target target = new TargetArtifactPermanent();
        getLeftHalfCard().getSpellAbility().addTarget(target);

        // Tear
        // Destroy target enchantment.
        getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        target = new TargetEnchantmentPermanent();
        getRightHalfCard().getSpellAbility().addTarget(target);

        // Fuse (You may cast one or both halves of this card from your hand.)
    }

    private WearTear(final WearTear card) {
        super(card);
    }

    @Override
    public WearTear copy() {
        return new WearTear(this);
    }
}
