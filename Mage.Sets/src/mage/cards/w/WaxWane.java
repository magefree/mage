
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LoneFox
 */
public final class WaxWane extends SplitCard {

    public WaxWane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}", "{W}", SpellAbilityType.SPLIT);

        // Wax
        // Target creature gets +2/+2 until end of turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Wane
        // Destroy target enchantment.
        this.getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetEnchantmentPermanent());
    }

    private WaxWane(final WaxWane card) {
        super(card);
    }

    @Override
    public WaxWane copy() {
        return new WaxWane(this);
    }
}
