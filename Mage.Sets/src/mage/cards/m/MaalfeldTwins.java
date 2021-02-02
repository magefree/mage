
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author Loki
 */
public final class MaalfeldTwins extends CardImpl {

    public MaalfeldTwins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Maalfeld Twins dies, create two 2/2 black Zombie creature tokens.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ZombieToken(), 2)));
    }

    private MaalfeldTwins(final MaalfeldTwins card) {
        super(card);
    }

    @Override
    public MaalfeldTwins copy() {
        return new MaalfeldTwins(this);
    }
}
