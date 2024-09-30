package mage.cards.s;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SazacapsBrew extends CardImpl {

    public SazacapsBrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Gift a tapped Fish
        this.addAbility(new GiftAbility(this, GiftType.TAPPED_FISH));

        // As an additional cost to cast this spell, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());

        // Target player draws two cards. If the gift was promised, target creature you control gets +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(2, 0)),
                GiftWasPromisedCondition.TRUE, "if the gift was promised, " +
                "target creature you control gets +2/+0 until end of turn"
        ).setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(
                GiftWasPromisedCondition.TRUE, true, new TargetControlledCreaturePermanent()
        ));
    }

    private SazacapsBrew(final SazacapsBrew card) {
        super(card);
    }

    @Override
    public SazacapsBrew copy() {
        return new SazacapsBrew(this);
    }
}
