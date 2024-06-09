package mage.cards.b;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class BalaGedRecovery extends ModalDoubleFacedCard {

    public BalaGedRecovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{2}{G}",
                "Bala Ged Sanctuary", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Bala Ged Recovery
        // Sorcery

        // Return target card from your graveyard to your hand.
        this.getLeftHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard());

        // 2.
        // Bala Ged Sanctuary
        // Land

        // Bala Ged Sanctuary enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());
    }

    private BalaGedRecovery(final BalaGedRecovery card) {
        super(card);
    }

    @Override
    public BalaGedRecovery copy() {
        return new BalaGedRecovery(this);
    }
}
