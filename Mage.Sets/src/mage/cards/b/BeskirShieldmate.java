package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HumanWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeskirShieldmate extends CardImpl {

    public BeskirShieldmate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Beskir Shieldmate dies, create a 1/1 white Human Warrior creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new HumanWarriorToken())));
    }

    private BeskirShieldmate(final BeskirShieldmate card) {
        super(card);
    }

    @Override
    public BeskirShieldmate copy() {
        return new BeskirShieldmate(this);
    }
}
