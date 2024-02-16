
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class AleshasVanguard extends CardImpl {

    public AleshasVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Dash {2}{B}
        this.addAbility(new DashAbility("{2}{B}"));
    }

    private AleshasVanguard(final AleshasVanguard card) {
        super(card);
    }

    @Override
    public AleshasVanguard copy() {
        return new AleshasVanguard(this);
    }
}
