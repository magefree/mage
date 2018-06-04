

package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author Loki
 */
public final class IsamaruHoundofKonda extends CardImpl {

    public IsamaruHoundofKonda (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HOUND);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    public IsamaruHoundofKonda (final IsamaruHoundofKonda card) {
        super(card);
    }

    @Override
    public IsamaruHoundofKonda copy() {
        return new IsamaruHoundofKonda(this);
    }

}
