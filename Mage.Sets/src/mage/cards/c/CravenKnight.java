
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
public final class CravenKnight extends CardImpl {

    public CravenKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Craven Knight can't block.
        this.addAbility(new CantBlockAbility());
    }

    private CravenKnight(final CravenKnight card) {
        super(card);
    }

    @Override
    public CravenKnight copy() {
        return new CravenKnight(this);
    }
}
