
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class ShuGeneral extends CardImpl {

    public ShuGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance; horsemanship
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(HorsemanshipAbility.getInstance());
    }

    private ShuGeneral(final ShuGeneral card) {
        super(card);
    }

    @Override
    public ShuGeneral copy() {
        return new ShuGeneral(this);
    }
}
