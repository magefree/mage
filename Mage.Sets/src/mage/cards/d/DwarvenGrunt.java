
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class DwarvenGrunt extends CardImpl {

    public DwarvenGrunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.DWARF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Mountainwalk
        this.addAbility(new MountainwalkAbility());
    }

    private DwarvenGrunt(final DwarvenGrunt card) {
        super(card);
    }

    @Override
    public DwarvenGrunt copy() {
        return new DwarvenGrunt(this);
    }
}
