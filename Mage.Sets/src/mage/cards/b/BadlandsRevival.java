package mage.cards.b;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BadlandsRevival extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public BadlandsRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{G}");

        // Return up to one target creature card from your graveyard to the battlefield. Return up to one target permanent card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect().setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filter));
    }

    private BadlandsRevival(final BadlandsRevival card) {
        super(card);
    }

    @Override
    public BadlandsRevival copy() {
        return new BadlandsRevival(this);
    }
}
