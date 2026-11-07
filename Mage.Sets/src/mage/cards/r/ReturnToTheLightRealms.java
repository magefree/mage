package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class ReturnToTheLightRealms extends CardImpl {

    public ReturnToTheLightRealms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{W}{W}");

        // Return all nonland permanent cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(StaticFilters.FILTER_CARDS_NON_LAND_PERMANENT));
    }

    private ReturnToTheLightRealms(final ReturnToTheLightRealms card) {
        super(card);
    }

    @Override
    public ReturnToTheLightRealms copy() {
        return new ReturnToTheLightRealms(this);
    }
}
