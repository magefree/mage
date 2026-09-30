package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class RestoreWithEmpathy extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public RestoreWithEmpathy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Return target permanent card from your graveyard to your hand. You gain 4 life.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
    }

    private RestoreWithEmpathy(final RestoreWithEmpathy card) {
        super(card);
    }

    @Override
    public RestoreWithEmpathy copy() {
        return new RestoreWithEmpathy(this);
    }
}
