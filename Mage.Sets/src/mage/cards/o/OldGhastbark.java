
package mage.cards.o;

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
public final class OldGhastbark extends CardImpl {

    public OldGhastbark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G/W}{G/W}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(6);
    }

    private OldGhastbark(final OldGhastbark card) {
        super(card);
    }

    @Override
    public OldGhastbark copy() {
        return new OldGhastbark(this);
    }
}
