
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class RecklessRacer extends CardImpl {

    public RecklessRacer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Reckless Racer becomes tapped, you may discard a card. If you do, draw a card.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()), false));
    }

    private RecklessRacer(final RecklessRacer card) {
        super(card);
    }

    @Override
    public RecklessRacer copy() {
        return new RecklessRacer(this);
    }
}
