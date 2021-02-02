
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ViashinoWarrior extends CardImpl {

    public ViashinoWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);
    }

    private ViashinoWarrior(final ViashinoWarrior card) {
        super(card);
    }

    @Override
    public ViashinoWarrior copy() {
        return new ViashinoWarrior(this);
    }
}
