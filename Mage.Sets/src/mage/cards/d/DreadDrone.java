

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EldraziSpawnToken;

/**
 *
 * @author Loki
 */
public final class DreadDrone extends CardImpl {

    public DreadDrone (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new EldraziSpawnToken(), 2)));
    }

    private DreadDrone(final DreadDrone card) {
        super(card);
    }

    @Override
    public DreadDrone copy() {
        return new DreadDrone(this);
    }

}
