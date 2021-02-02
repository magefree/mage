
package mage.cards.s;

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
public final class StrawSoldiers extends CardImpl {

    public StrawSoldiers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.SCARECROW);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
    }

    private StrawSoldiers(final StrawSoldiers card) {
        super(card);
    }

    @Override
    public StrawSoldiers copy() {
        return new StrawSoldiers(this);
    }
}
