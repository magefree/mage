
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class OrazcaRaptor extends CardImpl {

    public OrazcaRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
    }

    private OrazcaRaptor(final OrazcaRaptor card) {
        super(card);
    }

    @Override
    public OrazcaRaptor copy() {
        return new OrazcaRaptor(this);
    }
}
