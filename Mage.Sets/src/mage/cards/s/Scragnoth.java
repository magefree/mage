
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author shieldal
 */
public final class Scragnoth extends CardImpl {

    public Scragnoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Scragnoth can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());
        // Protection from blue
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE));
    }

    private Scragnoth(final Scragnoth card) {
        super(card);
    }

    @Override
    public Scragnoth copy() {
        return new Scragnoth(this);
    }
}
