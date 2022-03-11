package mage.cards.r;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Refurbish extends CardImpl {

    public Refurbish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Return target artifact card from your graveyard to the battlefield.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
    }

    private Refurbish(final Refurbish card) {
        super(card);
    }

    @Override
    public Refurbish copy() {
        return new Refurbish(this);
    }
}
