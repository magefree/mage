
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author ciaccona007
 */
public final class HarrierNaga extends CardImpl {

    public HarrierNaga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private HarrierNaga(final HarrierNaga card) {
        super(card);
    }

    @Override
    public HarrierNaga copy() {
        return new HarrierNaga(this);
    }
}
