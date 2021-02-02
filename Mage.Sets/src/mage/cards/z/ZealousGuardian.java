
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ZealousGuardian extends CardImpl {

    public ZealousGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W/U}");
        this.subtype.add(SubType.KITHKIN, SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlashAbility.getInstance());
    }

    private ZealousGuardian(final ZealousGuardian card) {
        super(card);
    }

    @Override
    public ZealousGuardian copy() {
        return new ZealousGuardian(this);
    }
}
