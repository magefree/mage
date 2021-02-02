
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class RaptorCompanion extends CardImpl {

    public RaptorCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
    }

    private RaptorCompanion(final RaptorCompanion card) {
        super(card);
    }

    @Override
    public RaptorCompanion copy() {
        return new RaptorCompanion(this);
    }
}
