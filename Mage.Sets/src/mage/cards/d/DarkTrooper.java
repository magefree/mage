
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class DarkTrooper extends CardImpl {

    public DarkTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.DROID);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Repair 2
        this.addAbility(new RepairAbility(2));
    }

    private DarkTrooper(final DarkTrooper card) {
        super(card);
    }

    @Override
    public DarkTrooper copy() {
        return new DarkTrooper(this);
    }
}
