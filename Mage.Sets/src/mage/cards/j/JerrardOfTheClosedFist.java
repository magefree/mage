
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author North
 */
public final class JerrardOfTheClosedFist extends CardImpl {

    public JerrardOfTheClosedFist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
    }

    private JerrardOfTheClosedFist(final JerrardOfTheClosedFist card) {
        super(card);
    }

    @Override
    public JerrardOfTheClosedFist copy() {
        return new JerrardOfTheClosedFist(this);
    }
}
