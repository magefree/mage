
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class ElvishRanger extends CardImpl {

    public ElvishRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF, SubType.RANGER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
    }

    private ElvishRanger(final ElvishRanger card) {
        super(card);
    }

    @Override
    public ElvishRanger copy() {
        return new ElvishRanger(this);
    }
}
