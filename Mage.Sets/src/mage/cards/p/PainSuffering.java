
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.target.TargetPlayer;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class PainSuffering extends SplitCard {

    public PainSuffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}", "{3}{R}", SpellAbilityType.SPLIT);

        // Pain
        // Target player discards a card.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DiscardTargetEffect(1));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetPlayer());

        // Suffering
        // Destroy target land.
        this.getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private PainSuffering(final PainSuffering card) {
        super(card);
    }

    @Override
    public PainSuffering copy() {
        return new PainSuffering(this);
    }
}
