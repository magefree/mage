
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class GreatHornKrushok extends CardImpl {

    public GreatHornKrushok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
    }

    private GreatHornKrushok(final GreatHornKrushok card) {
        super(card);
    }

    @Override
    public GreatHornKrushok copy() {
        return new GreatHornKrushok(this);
    }
}
