
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SilentArtisan extends CardImpl {

    public SilentArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
    }

    private SilentArtisan(final SilentArtisan card) {
        super(card);
    }

    @Override
    public SilentArtisan copy() {
        return new SilentArtisan(this);
    }
}
