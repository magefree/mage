
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PrimalHuntbeast extends CardImpl {

    public PrimalHuntbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
    }

    private PrimalHuntbeast(final PrimalHuntbeast card) {
        super(card);
    }

    @Override
    public PrimalHuntbeast copy() {
        return new PrimalHuntbeast(this);
    }
}
