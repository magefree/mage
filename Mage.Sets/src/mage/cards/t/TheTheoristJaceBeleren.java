package mage.cards.t;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfDrawTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Illusion11Token;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author muz
 */
public final class TheTheoristJaceBeleren extends CardImpl {

    public TheTheoristJaceBeleren(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);
        this.setStartingLoyalty(3);

        // At the beginning of each opponent's draw step, you draw a card.
        this.addAbility(new BeginningOfDrawTriggeredAbility(
            TargetController.OPPONENT,
            new DrawCardSourceControllerEffect(1, true),
            false
        ));

        // +1: Create a 1/1 blue Illusion creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new Illusion11Token()), 1));

        // -2: For each opponent, return up to one target artifact or creature that player controls to its owner's hand.
        LoyaltyAbility ability = new LoyaltyAbility(new ReturnToHandTargetEffect().setTargetPointer(new EachTargetPointer())
            .setText("for each opponent, return up to one target artifact or creature that player controls to its owner's hand"), -2);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(ability);

        // -6 Draw three cards. Then put X +1/+1 counters on each creature you control, where X is the number of cards in your hand.
        LoyaltyAbility ability2 = new LoyaltyAbility(new DrawCardSourceControllerEffect(3), -6);
        ability2.addEffect(
            new AddCountersAllEffect(
                CounterType.P1P1.createInstance(),
                CardsInControllerHandCount.ANY,
                StaticFilters.FILTER_CONTROLLED_CREATURE
            ).setText("Then put X +1/+1 counters on each creature you control, where X is the number of cards in your hand"));
        this.addAbility(ability2);
    }

    private TheTheoristJaceBeleren(final TheTheoristJaceBeleren card) {
        super(card);
    }

    @Override
    public TheTheoristJaceBeleren copy() {
        return new TheTheoristJaceBeleren(this);
    }
}
