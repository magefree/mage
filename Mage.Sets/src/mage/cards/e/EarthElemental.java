

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class EarthElemental extends CardImpl {

    public EarthElemental (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
    }

    private EarthElemental(final EarthElemental card) {
        super(card);
    }

    @Override
    public EarthElemental copy() {
        return new EarthElemental(this);
    }

}
