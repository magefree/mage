
package mage.cards.s;

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
 * @author Jgod
 */
public final class ShivanZombie extends CardImpl {

    public ShivanZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{R}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BARBARIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
    }

    private ShivanZombie(final ShivanZombie card) {
        super(card);
    }

    @Override
    public ShivanZombie copy() {
        return new ShivanZombie(this);
    }
}
