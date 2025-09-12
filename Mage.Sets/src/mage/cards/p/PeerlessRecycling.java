package mage.cards.p;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PeerlessRecycling extends CardImpl {

    public PeerlessRecycling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Gift a card
        this.addAbility(new GiftAbility(this, GiftType.CARD));

        // Return target permanent from your graveyard to your hand. If the gift was promised, instead return two target permanent cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect()
                .setText("return target permanent card from your graveyard to your hand. If the gift was promised, " +
                        "instead return two target permanent cards from your graveyard to your hand"));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_PERMANENT));
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(GiftWasPromisedCondition.TRUE,
                new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARD_PERMANENTS)));
    }

    private PeerlessRecycling(final PeerlessRecycling card) {
        super(card);
    }

    @Override
    public PeerlessRecycling copy() {
        return new PeerlessRecycling(this);
    }
}
