package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AkkiRonin extends CardImpl {

    public AkkiRonin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a Samurai or Warrior you control attacks alone, you may discard a card. If you do, draw a card.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        ), StaticFilters.FILTER_CONTROLLED_SAMURAI_OR_WARRIOR, false, false));
    }

    private AkkiRonin(final AkkiRonin card) {
        super(card);
    }

    @Override
    public AkkiRonin copy() {
        return new AkkiRonin(this);
    }
}
