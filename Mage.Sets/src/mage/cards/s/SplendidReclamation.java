package mage.cards.s;

import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SplendidReclamation extends CardImpl {

    public SplendidReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Return all land cards from your graveyard to the battlefield tapped.
        this.getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(StaticFilters.FILTER_CARD_LANDS, true));
    }

    private SplendidReclamation(final SplendidReclamation card) {
        super(card);
    }

    @Override
    public SplendidReclamation copy() {
        return new SplendidReclamation(this);
    }
}
