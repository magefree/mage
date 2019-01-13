package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurningTreeVandal extends CardImpl {

    public BurningTreeVandal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Riot
        this.addAbility(new RiotAbility());

        // Whenever Burning-Tree Vandal attacks, you may discard a card. If you do, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        ), false));
    }

    private BurningTreeVandal(final BurningTreeVandal card) {
        super(card);
    }

    @Override
    public BurningTreeVandal copy() {
        return new BurningTreeVandal(this);
    }
}
