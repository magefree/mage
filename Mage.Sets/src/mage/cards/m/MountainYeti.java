
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.MountainwalkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class MountainYeti extends CardImpl {

    public MountainYeti(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.YETI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Mountainwalk
        this.addAbility(new MountainwalkAbility());
        // protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
    }

    private MountainYeti(final MountainYeti card) {
        super(card);
    }

    @Override
    public MountainYeti copy() {
        return new MountainYeti(this);
    }
}
