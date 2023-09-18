package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PowerstoneEngineer extends CardImpl {

    public PowerstoneEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Powerstone Engineer dies, create a tapped Powerstone token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new PowerstoneToken(), 1, true)));
    }

    private PowerstoneEngineer(final PowerstoneEngineer card) {
        super(card);
    }

    @Override
    public PowerstoneEngineer copy() {
        return new PowerstoneEngineer(this);
    }
}
