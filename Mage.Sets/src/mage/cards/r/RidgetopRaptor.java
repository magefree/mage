
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RidgetopRaptor extends CardImpl {

    public RidgetopRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private RidgetopRaptor(final RidgetopRaptor card) {
        super(card);
    }

    @Override
    public RidgetopRaptor copy() {
        return new RidgetopRaptor(this);
    }
}
