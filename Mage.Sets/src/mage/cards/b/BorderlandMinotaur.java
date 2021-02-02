
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BorderlandMinotaur extends CardImpl {

    public BorderlandMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.MINOTAUR, SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private BorderlandMinotaur(final BorderlandMinotaur card) {
        super(card);
    }

    @Override
    public BorderlandMinotaur copy() {
        return new BorderlandMinotaur(this);
    }
}
