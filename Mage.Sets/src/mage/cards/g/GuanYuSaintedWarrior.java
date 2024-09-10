
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
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
public final class GuanYuSaintedWarrior extends CardImpl {

    public GuanYuSaintedWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
        // When Guan Yu, Sainted Warrior is put into your graveyard from the battlefield, you may shuffle Guan Yu into your library.
        this.addAbility(new DiesSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect(), true));
    }

    private GuanYuSaintedWarrior(final GuanYuSaintedWarrior card) {
        super(card);
    }

    @Override
    public GuanYuSaintedWarrior copy() {
        return new GuanYuSaintedWarrior(this);
    }
}
