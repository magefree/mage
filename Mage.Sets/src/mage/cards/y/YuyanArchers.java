package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YuyanArchers extends CardImpl {

    public YuyanArchers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When this creature enters, you may discard a card. If you do, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost())
        ));
    }

    private YuyanArchers(final YuyanArchers card) {
        super(card);
    }

    @Override
    public YuyanArchers copy() {
        return new YuyanArchers(this);
    }
}
