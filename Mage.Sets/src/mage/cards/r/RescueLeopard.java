package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RescueLeopard extends CardImpl {

    public RescueLeopard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever this creature becomes tapped, you may discard a card. If you do, draw a card.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost())
        ));
    }

    private RescueLeopard(final RescueLeopard card) {
        super(card);
    }

    @Override
    public RescueLeopard copy() {
        return new RescueLeopard(this);
    }
}
