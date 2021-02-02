
package mage.cards.o;

import java.util.UUID;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author JayDi85
 */
public final class OrazcaFrillback extends CardImpl {

    public OrazcaFrillback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);
    }

    private OrazcaFrillback(final OrazcaFrillback card) {
        super(card);
    }

    @Override
    public OrazcaFrillback copy() {
        return new OrazcaFrillback(this);
    }
}