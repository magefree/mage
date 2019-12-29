package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LochDragon extends CardImpl {

    public LochDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/R}{U/R}{U/R}{U/R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Loch Dragon enters the battlefield or attacks, you may discard a card. If you do, draw a card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost())
        ));
    }

    private LochDragon(final LochDragon card) {
        super(card);
    }

    @Override
    public LochDragon copy() {
        return new LochDragon(this);
    }
}
