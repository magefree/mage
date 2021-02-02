
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BeastToken;

/**
 *
 * @author North
 */
public final class Thragtusk extends CardImpl {

    public Thragtusk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);


        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // When Thragtusk enters the battlefield, you gain 5 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(5)));

        // When Thragtusk leaves the battlefield, create a 3/3 green Beast creature token.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new CreateTokenEffect(new BeastToken()), false));
    }

    private Thragtusk(final Thragtusk card) {
        super(card);
    }

    @Override
    public Thragtusk copy() {
        return new Thragtusk(this);
    }
}
