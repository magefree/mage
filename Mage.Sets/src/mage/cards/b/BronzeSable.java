
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BronzeSable extends CardImpl {

    public BronzeSable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.SABLE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private BronzeSable(final BronzeSable card) {
        super(card);
    }

    @Override
    public BronzeSable copy() {
        return new BronzeSable(this);
    }
}
