package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class T45PowerArmor extends CardImpl {

    public T45PowerArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // When T-45 Power Armor enters the battlefield, you get {E}{E}.
        // Equipped creature gets +3/+3 and doesn't untap during its controller's untap step.
        // At the beginning of your upkeep, you may pay {E}. If you do, untap equipped creature, then put your choice of a menace, trample or lifelink counter on it.
        // Equip {3}
    }

    private T45PowerArmor(final T45PowerArmor card) {
        super(card);
    }

    @Override
    public T45PowerArmor copy() {
        return new T45PowerArmor(this);
    }
}
