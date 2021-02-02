
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class CravenGiant extends CardImpl {

    public CravenGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Craven Giant can't block.
        this.addAbility(new CantBlockAbility());
    }

    private CravenGiant(final CravenGiant card) {
        super(card);
    }

    @Override
    public CravenGiant copy() {
        return new CravenGiant(this);
    }
}
