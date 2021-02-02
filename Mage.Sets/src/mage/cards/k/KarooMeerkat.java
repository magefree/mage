
package mage.cards.k;

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
public final class KarooMeerkat extends CardImpl {

    public KarooMeerkat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.MONGOOSE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from blue
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE));
    }

    private KarooMeerkat(final KarooMeerkat card) {
        super(card);
    }

    @Override
    public KarooMeerkat copy() {
        return new KarooMeerkat(this);
    }
}
