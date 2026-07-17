package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BlackAndRedGoblinToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SourbreadAuntie extends CardImpl {

    public SourbreadAuntie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When this creature enters, you may blight 2. If you do, create two 1/1 black and red Goblin creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new BlackAndRedGoblinToken(), 2), new BlightCost(2)
        )));
    }

    private SourbreadAuntie(final SourbreadAuntie card) {
        super(card);
    }

    @Override
    public SourbreadAuntie copy() {
        return new SourbreadAuntie(this);
    }
}
