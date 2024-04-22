package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.HumanKnightToken;

/**
 *
 * @author Susucr
 */
public final class RidersOfRohan extends CardImpl {

    public RidersOfRohan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Riders of Rohan enters the battlefield, create two 2/2 red Human Knight creature tokens with trample and haste.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanKnightToken(),2)));

        // Dash {4}{R}{W}
        this.addAbility(new DashAbility("{4}{R}{W}"));

    }

    private RidersOfRohan(final RidersOfRohan card) {
        super(card);
    }

    @Override
    public RidersOfRohan copy() {
        return new RidersOfRohan(this);
    }
}
