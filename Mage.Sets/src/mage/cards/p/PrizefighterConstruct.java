
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class PrizefighterConstruct extends CardImpl {

    public PrizefighterConstruct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(2);
    }

    private PrizefighterConstruct(final PrizefighterConstruct card) {
        super(card);
    }

    @Override
    public PrizefighterConstruct copy() {
        return new PrizefighterConstruct(this);
    }
}
