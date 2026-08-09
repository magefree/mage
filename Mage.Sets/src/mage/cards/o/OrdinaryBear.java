package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class OrdinaryBear extends CardImpl {

    public OrdinaryBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
    }

    private OrdinaryBear(final OrdinaryBear card) {
        super(card);
    }

    @Override
    public OrdinaryBear copy() {
        return new OrdinaryBear(this);
    }
}
