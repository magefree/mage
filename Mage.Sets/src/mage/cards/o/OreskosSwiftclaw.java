
package mage.cards.o;

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
public final class OreskosSwiftclaw extends CardImpl {

    public OreskosSwiftclaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
    }

    private OreskosSwiftclaw(final OreskosSwiftclaw card) {
        super(card);
    }

    @Override
    public OreskosSwiftclaw copy() {
        return new OreskosSwiftclaw(this);
    }
}
