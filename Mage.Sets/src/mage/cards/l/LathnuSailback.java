
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class LathnuSailback extends CardImpl {

    public LathnuSailback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
    }

    private LathnuSailback(final LathnuSailback card) {
        super(card);
    }

    @Override
    public LathnuSailback copy() {
        return new LathnuSailback(this);
    }
}
