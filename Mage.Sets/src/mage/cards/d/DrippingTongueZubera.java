

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.dynamicvalue.common.ZuberasDiedDynamicValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SpiritToken;
import mage.watchers.common.ZuberasDiedWatcher;

/**
 *
 * @author Loki
 */
public final class DrippingTongueZubera extends CardImpl {

    public DrippingTongueZubera (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ZUBERA);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.addAbility(new DiesTriggeredAbility(new CreateTokenEffect(new SpiritToken(), new ZuberasDiedDynamicValue()), false), new ZuberasDiedWatcher());
    }

    public DrippingTongueZubera (final DrippingTongueZubera card) {
        super(card);
    }

    @Override
    public DrippingTongueZubera copy() {
        return new DrippingTongueZubera(this);
    }

}
