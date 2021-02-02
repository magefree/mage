
package mage.cards.a;

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
public final class AlpineGrizzly extends CardImpl {

    public AlpineGrizzly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BEAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);
    }

    private AlpineGrizzly(final AlpineGrizzly card) {
        super(card);
    }

    @Override
    public AlpineGrizzly copy() {
        return new AlpineGrizzly(this);
    }
}
