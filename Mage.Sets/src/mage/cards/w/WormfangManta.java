
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.effects.common.turn.SkipNextTurnSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class WormfangManta extends CardImpl {

    public WormfangManta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Wormfang Manta enters the battlefield, you skip your next turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SkipNextTurnSourceEffect()));
        // When Wormfang Manta leaves the battlefield, you take an extra turn after this one.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new AddExtraTurnControllerEffect(), false));
    }

    private WormfangManta(final WormfangManta card) {
        super(card);
    }

    @Override
    public WormfangManta copy() {
        return new WormfangManta(this);
    }
}
