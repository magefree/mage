

package mage.cards.n;

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
public final class NemaSiltlurker extends CardImpl {

    public NemaSiltlurker (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
    }

    private NemaSiltlurker(final NemaSiltlurker card) {
        super(card);
    }

    @Override
    public NemaSiltlurker copy() {
        return new NemaSiltlurker(this);
    }

}
