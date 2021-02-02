

package mage.cards.e;

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
public final class EmrakulsHatcher extends CardImpl {

    public EmrakulsHatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new EldraziSpawnToken(), 3), false));
    }

    private EmrakulsHatcher(final EmrakulsHatcher card) {
        super(card);
    }

    @Override
    public EmrakulsHatcher copy() {
        return new EmrakulsHatcher(this);
    }

}
