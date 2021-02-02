
package mage.cards.r;

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
 * @author KholdFuzion
 */
public final class RepentantBlacksmith extends CardImpl {

    public RepentantBlacksmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
    }

    private RepentantBlacksmith(final RepentantBlacksmith card) {
        super(card);
    }

    @Override
    public RepentantBlacksmith copy() {
        return new RepentantBlacksmith(this);
    }
}
