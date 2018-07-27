package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.HasteAbility;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.ZergToken;

/**
 *
 * @author NinthWorld
 */
public final class ZergEgg extends CardImpl {

    public ZergEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        
        this.subtype.add(SubType.ZERG);
        this.subtype.add(SubType.EGG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // At the beginning of your upkeep, you may sacrifice Zerg Egg. If you do, put two 1/1 green Zerg creature tokens with haste onto the battlefield.
        Token token = new ZergToken();
        token.addAbility(HasteAbility.getInstance());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DoIfCostPaid(
                new CreateTokenEffect(token, 2)
                        .setText("put two 1/1 green Zerg creature tokens with haste onto the battlefield"),
                new SacrificeSourceCost()), TargetController.YOU, false));
    }

    public ZergEgg(final ZergEgg card) {
        super(card);
    }

    @Override
    public ZergEgg copy() {
        return new ZergEgg(this);
    }
}
