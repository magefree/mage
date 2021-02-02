
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
public final class OmegaMyr extends CardImpl {

    public OmegaMyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
    }

    private OmegaMyr(final OmegaMyr card) {
        super(card);
    }

    @Override
    public OmegaMyr copy() {
        return new OmegaMyr(this);
    }
}
