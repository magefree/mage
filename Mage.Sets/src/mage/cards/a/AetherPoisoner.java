
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ServoToken;

/**
 *
 * @author fireshoes
 */
public final class AetherPoisoner extends CardImpl {

    public AetherPoisoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Aether Poisoner enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // Whenever Aether Poisoner attacks, you may pay {E}{E}. If you do, create a 1/1 colorless Servo artifact creature token.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(new CreateTokenEffect(new ServoToken()), new PayEnergyCost(2)), false,
                "Whenever {this} attacks, you may pay {E}{E}. If you do, create a 1/1 colorless Servo artifact creature token."));
    }

    private AetherPoisoner(final AetherPoisoner card) {
        super(card);
    }

    @Override
    public AetherPoisoner copy() {
        return new AetherPoisoner(this);
    }
}
