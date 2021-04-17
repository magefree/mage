package mage.cards.r;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author KholdFuzion
 */
public final class Regrowth extends CardImpl {

    public Regrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Return target card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard());
    }

    private Regrowth(final Regrowth card) {
        super(card);
    }

    @Override
    public Regrowth copy() {
        return new Regrowth(this);
    }
}
