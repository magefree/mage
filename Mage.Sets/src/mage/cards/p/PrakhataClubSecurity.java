
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class PrakhataClubSecurity extends CardImpl {

    public PrakhataClubSecurity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
    }

    private PrakhataClubSecurity(final PrakhataClubSecurity card) {
        super(card);
    }

    @Override
    public PrakhataClubSecurity copy() {
        return new PrakhataClubSecurity(this);
    }
}
