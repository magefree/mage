
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.AssistAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class ChargingBinox extends CardImpl {

    public ChargingBinox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Assist (Another player can pay up to {7} of this spell's cost.)
        this.addAbility(new AssistAbility());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private ChargingBinox(final ChargingBinox card) {
        super(card);
    }

    @Override
    public ChargingBinox copy() {
        return new ChargingBinox(this);
    }
}
