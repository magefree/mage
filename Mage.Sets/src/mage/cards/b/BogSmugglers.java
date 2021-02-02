
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class BogSmugglers extends CardImpl {

    public BogSmugglers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.HUMAN, SubType.MERCENARY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());
    }

    private BogSmugglers(final BogSmugglers card) {
        super(card);
    }

    @Override
    public BogSmugglers copy() {
        return new BogSmugglers(this);
    }
}
