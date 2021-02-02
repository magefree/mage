
package mage.cards.b;

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
public final class BarbarianGeneral extends CardImpl {

    public BarbarianGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.HUMAN, SubType.BARBARIAN, SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
    }

    private BarbarianGeneral(final BarbarianGeneral card) {
        super(card);
    }

    @Override
    public BarbarianGeneral copy() {
        return new BarbarianGeneral(this);
    }
}
