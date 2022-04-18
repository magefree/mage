
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jeffwadsworth
 */
public final class Vaultbreaker extends CardImpl {

    public Vaultbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever Vaultbreaker attacks, you may discard a card. If you do, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1),
                new DiscardCardCost()), false, "Whenever {this} attacks, you may discard a card. If you do, draw a card"));

        // Dash {2}{R}
        this.addAbility(new DashAbility("{2}{R}"));

    }

    private Vaultbreaker(final Vaultbreaker card) {
        super(card);
    }

    @Override
    public Vaultbreaker copy() {
        return new Vaultbreaker(this);
    }
}
