package mage.cards.z;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZukosConviction extends CardImpl {

    public ZukosConviction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));

        // Return target creature card from your graveyard to your hand. If this spell was kicked, instead put that card onto the battlefield tapped.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true),
                new ReturnFromGraveyardToHandTargetEffect(),
                KickedCondition.ONCE, "return target creature card from your graveyard to your hand. " +
                "If this spell was kicked, instead put that card onto the battlefield tapped"
        ));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private ZukosConviction(final ZukosConviction card) {
        super(card);
    }

    @Override
    public ZukosConviction copy() {
        return new ZukosConviction(this);
    }
}
