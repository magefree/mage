

package mage.cards.k;

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
public final class KamiOfOldStone extends CardImpl {

    public KamiOfOldStone (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(7);
    }

    private KamiOfOldStone(final KamiOfOldStone card) {
        super(card);
    }

    @Override
    public KamiOfOldStone copy() {
        return new KamiOfOldStone(this);
    }

}
