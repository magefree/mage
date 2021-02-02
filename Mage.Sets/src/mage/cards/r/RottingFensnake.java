
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RottingFensnake extends CardImpl {

    public RottingFensnake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(1);
    }

    private RottingFensnake(final RottingFensnake card) {
        super(card);
    }

    @Override
    public RottingFensnake copy() {
        return new RottingFensnake(this);
    }
}
