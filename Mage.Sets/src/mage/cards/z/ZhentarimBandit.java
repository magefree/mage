package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZhentarimBandit extends CardImpl {

    public ZhentarimBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Zhentarim Bandit attacks, you may pay 1 life. If you do, create a Treasure token.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new TreasureToken()), new PayLifeCost(1)
        )));
    }

    private ZhentarimBandit(final ZhentarimBandit card) {
        super(card);
    }

    @Override
    public ZhentarimBandit copy() {
        return new ZhentarimBandit(this);
    }
}
