
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class BalduvianBarbarians extends CardImpl {

    public BalduvianBarbarians(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.HUMAN, SubType.BARBARIAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private BalduvianBarbarians(final BalduvianBarbarians card) {
        super(card);
    }

    @Override
    public BalduvianBarbarians copy() {
        return new BalduvianBarbarians(this);
    }
}
