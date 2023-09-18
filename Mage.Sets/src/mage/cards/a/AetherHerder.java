
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ServoToken;

/**
 *
 * @author fireshoes
 */
public final class AetherHerder extends CardImpl {

    public AetherHerder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Aether Herder enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // Whenever Aether Herder attacks, you may pay {E}{E}. If you do, create a 1/1 colorless Servo artifact creature token.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(new CreateTokenEffect(new ServoToken()), new PayEnergyCost(2))));
    }

    private AetherHerder(final AetherHerder card) {
        super(card);
    }

    @Override
    public AetherHerder copy() {
        return new AetherHerder(this);
    }
}
