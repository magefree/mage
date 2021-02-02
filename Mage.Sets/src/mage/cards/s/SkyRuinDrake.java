
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SkyRuinDrake extends CardImpl {

    public SkyRuinDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
    }

    private SkyRuinDrake(final SkyRuinDrake card) {
        super(card);
    }

    @Override
    public SkyRuinDrake copy() {
        return new SkyRuinDrake(this);
    }
}
