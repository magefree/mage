
package mage.cards.f;

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
public final class FeralKrushok extends CardImpl {

    public FeralKrushok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
    }

    private FeralKrushok(final FeralKrushok card) {
        super(card);
    }

    @Override
    public FeralKrushok copy() {
        return new FeralKrushok(this);
    }
}
