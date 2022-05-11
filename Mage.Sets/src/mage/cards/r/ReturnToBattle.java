package mage.cards.r;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class ReturnToBattle extends CardImpl {

    public ReturnToBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private ReturnToBattle(final ReturnToBattle card) {
        super(card);
    }

    @Override
    public ReturnToBattle copy() {
        return new ReturnToBattle(this);
    }
}
