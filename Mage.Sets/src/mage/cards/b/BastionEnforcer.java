
package mage.cards.b;

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
public final class BastionEnforcer extends CardImpl {

    public BastionEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.DWARF, SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private BastionEnforcer(final BastionEnforcer card) {
        super(card);
    }

    @Override
    public BastionEnforcer copy() {
        return new BastionEnforcer(this);
    }
}
