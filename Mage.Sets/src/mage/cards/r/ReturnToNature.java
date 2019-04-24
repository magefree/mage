package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReturnToNature extends CardImpl {

    public ReturnToNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Choose one —
        // • Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // • Destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);

        // • Exile target card from a graveyard.
        mode = new Mode(new ExileTargetEffect());
        mode.addTarget(new TargetCardInGraveyard());
        this.getSpellAbility().addMode(mode);
    }

    private ReturnToNature(final ReturnToNature card) {
        super(card);
    }

    @Override
    public ReturnToNature copy() {
        return new ReturnToNature(this);
    }
}
