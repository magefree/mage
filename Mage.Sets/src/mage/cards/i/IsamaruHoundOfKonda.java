

package mage.cards.i;

import java.util.UUID;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author Loki
 */
public final class IsamaruHoundOfKonda extends CardImpl {

    public IsamaruHoundOfKonda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private IsamaruHoundOfKonda(final IsamaruHoundOfKonda card) {
        super(card);
    }

    @Override
    public IsamaruHoundOfKonda copy() {
        return new IsamaruHoundOfKonda(this);
    }

}
