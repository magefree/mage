
package mage.cards.l;

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
public final class LoomingAltisaur extends CardImpl {

    public LoomingAltisaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(7);
    }

    private LoomingAltisaur(final LoomingAltisaur card) {
        super(card);
    }

    @Override
    public LoomingAltisaur copy() {
        return new LoomingAltisaur(this);
    }
}
