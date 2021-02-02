
package mage.cards.d;

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
public final class DevilthornFox extends CardImpl {

    public DevilthornFox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.FOX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
    }

    private DevilthornFox(final DevilthornFox card) {
        super(card);
    }

    @Override
    public DevilthornFox copy() {
        return new DevilthornFox(this);
    }
}
