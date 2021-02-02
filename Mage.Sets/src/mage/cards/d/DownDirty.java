
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

public final class DownDirty extends SplitCard {

    public DownDirty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}", "{2}{G}", SpellAbilityType.SPLIT_FUSED);

        // Down
        // Target player discards two cards.
        getLeftHalfCard().getSpellAbility().addEffect(new DiscardTargetEffect(2));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPlayer());

        // Dirty
        // Return target card from your graveyard to your hand.
        getRightHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard());

    }

    private DownDirty(final DownDirty card) {
        super(card);
    }

    @Override
    public DownDirty copy() {
        return new DownDirty(this);
    }
}
