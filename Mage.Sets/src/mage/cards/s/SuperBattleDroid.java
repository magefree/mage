
package mage.cards.s;

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
public final class SuperBattleDroid extends CardImpl {

    public SuperBattleDroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.DROID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Repair 2
        this.addAbility(new RepairAbility(2));
    }

    private SuperBattleDroid(final SuperBattleDroid card) {
        super(card);
    }

    @Override
    public SuperBattleDroid copy() {
        return new SuperBattleDroid(this);
    }
}
