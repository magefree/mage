
package mage.cards.g;

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
 * @author michael.napoleon@gmail.com
 */
public final class GalinasKnight extends CardImpl {

    public GalinasKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
    }

    private GalinasKnight(final GalinasKnight card) {
        super(card);
    }

    @Override
    public GalinasKnight copy() {
        return new GalinasKnight(this);
    }
}