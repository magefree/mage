
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author ilcartographer
 */
public final class LuBuMasterAtArms extends CardImpl {

    public LuBuMasterAtArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Haste; horsemanship
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(HorsemanshipAbility.getInstance());
    }

    private LuBuMasterAtArms(final LuBuMasterAtArms card) {
        super(card);
    }

    @Override
    public LuBuMasterAtArms copy() {
        return new LuBuMasterAtArms(this);
    }
}
