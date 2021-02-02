
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class ObsidianGiant extends CardImpl {

    public ObsidianGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }

    private ObsidianGiant(final ObsidianGiant card) {
        super(card);
    }

    @Override
    public ObsidianGiant copy() {
        return new ObsidianGiant(this);
    }
}
