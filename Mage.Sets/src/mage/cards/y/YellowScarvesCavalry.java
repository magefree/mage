
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class YellowScarvesCavalry extends CardImpl {

    public YellowScarvesCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
        // Yellow Scarves Cavalry can't block.
        this.addAbility(new CantBlockAbility());
    }

    private YellowScarvesCavalry(final YellowScarvesCavalry card) {
        super(card);
    }

    @Override
    public YellowScarvesCavalry copy() {
        return new YellowScarvesCavalry(this);
    }
}
