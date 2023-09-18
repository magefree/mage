
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class LuMengWuGeneral extends CardImpl {

    public LuMengWuGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
    }

    private LuMengWuGeneral(final LuMengWuGeneral card) {
        super(card);
    }

    @Override
    public LuMengWuGeneral copy() {
        return new LuMengWuGeneral(this);
    }
}
