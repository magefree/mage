
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ServoToken;

/**
 *
 * @author fireshoes
 */
public final class AetherSwooper extends CardImpl {

    public AetherSwooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Aether Swooper enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // Whenever Aether Swooper attacks, you may pay {E}{E}. If you do, create a 1/1 colorless Servo artifact creature token.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(new CreateTokenEffect(new ServoToken()), new PayEnergyCost(2))));
    }

    private AetherSwooper(final AetherSwooper card) {
        super(card);
    }

    @Override
    public AetherSwooper copy() {
        return new AetherSwooper(this);
    }
}
