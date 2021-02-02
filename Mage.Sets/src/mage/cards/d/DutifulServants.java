
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Archer262
 */
public final class DutifulServants extends CardImpl {

    public DutifulServants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);
    }

    private DutifulServants(final DutifulServants card) {
        super(card);
    }

    @Override
    public DutifulServants copy() {
        return new DutifulServants(this);
    }
}