
package mage.cards.s;

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
public final class ShuCavalry extends CardImpl {

    public ShuCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
    }

    private ShuCavalry(final ShuCavalry card) {
        super(card);
    }

    @Override
    public ShuCavalry copy() {
        return new ShuCavalry(this);
    }
}
