
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class QueensBaySoldier extends CardImpl {

    public QueensBaySoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private QueensBaySoldier(final QueensBaySoldier card) {
        super(card);
    }

    @Override
    public QueensBaySoldier copy() {
        return new QueensBaySoldier(this);
    }
}
