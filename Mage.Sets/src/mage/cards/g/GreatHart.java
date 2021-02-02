
package mage.cards.g;

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
public final class GreatHart extends CardImpl {

    public GreatHart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.ELK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
    }

    private GreatHart(final GreatHart card) {
        super(card);
    }

    @Override
    public GreatHart copy() {
        return new GreatHart(this);
    }
}
