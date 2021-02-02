package mage.cards.s;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ShorecomberCrab extends CardImpl {

    public ShorecomberCrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.CRAB);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
    }

    private ShorecomberCrab(final ShorecomberCrab card) {
        super(card);
    }

    @Override
    public ShorecomberCrab copy() {
        return new ShorecomberCrab(this);
    }
}
