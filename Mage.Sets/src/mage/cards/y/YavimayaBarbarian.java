
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class YavimayaBarbarian extends CardImpl {

    public YavimayaBarbarian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from blue
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE));
    }

    private YavimayaBarbarian(final YavimayaBarbarian card) {
        super(card);
    }

    @Override
    public YavimayaBarbarian copy() {
        return new YavimayaBarbarian(this);
    }
}
