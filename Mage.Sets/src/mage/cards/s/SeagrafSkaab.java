
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
public final class SeagrafSkaab extends CardImpl {

    public SeagrafSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
    }

    private SeagrafSkaab(final SeagrafSkaab card) {
        super(card);
    }

    @Override
    public SeagrafSkaab copy() {
        return new SeagrafSkaab(this);
    }
}
