
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class WuEliteCavalry extends CardImpl {

    public WuEliteCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
    }

    private WuEliteCavalry(final WuEliteCavalry card) {
        super(card);
    }

    @Override
    public WuEliteCavalry copy() {
        return new WuEliteCavalry(this);
    }
}
