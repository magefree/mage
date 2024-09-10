
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author North
 */
public final class TorstenVonUrsus extends CardImpl {

    public TorstenVonUrsus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
    }

    private TorstenVonUrsus(final TorstenVonUrsus card) {
        super(card);
    }

    @Override
    public TorstenVonUrsus copy() {
        return new TorstenVonUrsus(this);
    }
}
