
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class HazeriderDrake extends CardImpl {

    public HazeriderDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{U}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
    }

    private HazeriderDrake(final HazeriderDrake card) {
        super(card);
    }

    @Override
    public HazeriderDrake copy() {
        return new HazeriderDrake(this);
    }
}
