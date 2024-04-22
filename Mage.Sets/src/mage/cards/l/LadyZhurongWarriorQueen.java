
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
public final class LadyZhurongWarriorQueen extends CardImpl {

    public LadyZhurongWarriorQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
    }

    private LadyZhurongWarriorQueen(final LadyZhurongWarriorQueen card) {
        super(card);
    }

    @Override
    public LadyZhurongWarriorQueen copy() {
        return new LadyZhurongWarriorQueen(this);
    }
}
