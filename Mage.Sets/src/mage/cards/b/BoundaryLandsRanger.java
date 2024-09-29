package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoundaryLandsRanger extends CardImpl {

    public BoundaryLandsRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, if you control a creature with power 4 or greater, you may discard a card. If you do, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new DoIfCostPaid(
                                new DrawCardSourceControllerEffect(1), new DiscardCardCost()
                        ), TargetController.YOU, false
                ), FerociousCondition.instance, "At the beginning of combat on your turn, if you control " +
                "a creature with power 4 or greater, you may discard a card. If you do, draw a card."
        ).addHint(FerociousHint.instance));
    }

    private BoundaryLandsRanger(final BoundaryLandsRanger card) {
        super(card);
    }

    @Override
    public BoundaryLandsRanger copy() {
        return new BoundaryLandsRanger(this);
    }
}
