
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class TerrorOfTheFairgrounds extends CardImpl {

    public TerrorOfTheFairgrounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GREMLIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);
    }

    private TerrorOfTheFairgrounds(final TerrorOfTheFairgrounds card) {
        super(card);
    }

    @Override
    public TerrorOfTheFairgrounds copy() {
        return new TerrorOfTheFairgrounds(this);
    }
}
