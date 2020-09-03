package mage.cards.b;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BalaGedRecovery extends CardImpl {

    public BalaGedRecovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.b.BalaGedSanctuary.class;

        // Return target card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard());
    }

    private BalaGedRecovery(final BalaGedRecovery card) {
        super(card);
    }

    @Override
    public BalaGedRecovery copy() {
        return new BalaGedRecovery(this);
    }
}
