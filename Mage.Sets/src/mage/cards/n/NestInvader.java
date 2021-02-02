

package mage.cards.n;

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
 * @author BetaSteward_at_googlemail.com
 */
public final class NestInvader extends CardImpl {

    public NestInvader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new EldraziSpawnToken()), false));
    }

    private NestInvader(final NestInvader card) {
        super(card);
    }

    @Override
    public NestInvader copy() {
        return new NestInvader(this);
    }

}