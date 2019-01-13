package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WatchfulGiant extends CardImpl {

    public WatchfulGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // When Watchful Giant enters the battlefield, create a 1/1 white Human creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanToken())));
    }

    private WatchfulGiant(final WatchfulGiant card) {
        super(card);
    }

    @Override
    public WatchfulGiant copy() {
        return new WatchfulGiant(this);
    }
}
