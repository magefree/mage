package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class RedDeathShipwrecker extends CardImpl {

    public RedDeathShipwrecker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Alluring Eyes -- {T}: Goad target creature an opponent controls. That player draws a card. You add {R}.
    }

    private RedDeathShipwrecker(final RedDeathShipwrecker card) {
        super(card);
    }

    @Override
    public RedDeathShipwrecker copy() {
        return new RedDeathShipwrecker(this);
    }
}
