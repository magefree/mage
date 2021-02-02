
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class FieldCreeper extends CardImpl {

    public FieldCreeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private FieldCreeper(final FieldCreeper card) {
        super(card);
    }

    @Override
    public FieldCreeper copy() {
        return new FieldCreeper(this);
    }
}
