package mage.cards.r;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Regenesis extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent cards from your graveyard");

    public Regenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{G}");

        // Return up to two target permanent cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, filter));
    }

    private Regenesis(final Regenesis card) {
        super(card);
    }

    @Override
    public Regenesis copy() {
        return new Regenesis(this);
    }
}
