package mage.cards.c;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.GreatestPowerControlledPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class ConsumedByGreed extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with the greatest power among creatures they control");

    static {
        filter.add(GreatestPowerControlledPredicate.instance);
    }

    public ConsumedByGreed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");


        // Gift a card
        this.addAbility(new GiftAbility(this, GiftType.CARD));

        // Target opponent sacrifices a creature with the greatest power among creatures they control. If the gift was promised, return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new SacrificeEffect(filter, 1, "Target opponent"));
        this.getSpellAbility().addTarget(new TargetOpponent());

        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ReturnFromGraveyardToHandTargetEffect(), GiftWasPromisedCondition.TRUE,
                "If the gift was promised, return target creature card from your graveyard to your hand.")
                .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(GiftWasPromisedCondition.TRUE, true,
                new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));
    }

    private ConsumedByGreed(final ConsumedByGreed card) {
        super(card);
    }

    @Override
    public ConsumedByGreed copy() {
        return new ConsumedByGreed(this);
    }
}
