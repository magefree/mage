package mage.cards.r;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Recollect extends CardImpl {

    public Recollect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Return target card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard());
    }

    private Recollect(final Recollect card) {
        super(card);
    }

    @Override
    public Recollect copy() {
        return new Recollect(this);
    }
}
