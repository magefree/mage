package mage.cards.p;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PullThroughTheWeft extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("nonland permanent cards from your graveyard");

    static {
        filter.add(PermanentPredicate.instance);
    }

    public PullThroughTheWeft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Return up to two target nonland permanent cards from your graveyard to your hand, then return up to two target land cards from your graveyard to the battlefield tapped.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, filter));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect(true)
                .setTargetPointer(new SecondTargetPointer())
                .setText(", then return up to two target land cards from your graveyard to the battlefield tapped"));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 2, StaticFilters.FILTER_CARD_LAND_FROM_YOUR_GRAVEYARD
        ));
    }

    private PullThroughTheWeft(final PullThroughTheWeft card) {
        super(card);
    }

    @Override
    public PullThroughTheWeft copy() {
        return new PullThroughTheWeft(this);
    }
}
