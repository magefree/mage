

package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LoxodonWayfarer extends CardImpl {

    public LoxodonWayfarer (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);
    }

    private LoxodonWayfarer(final LoxodonWayfarer card) {
        super(card);
    }

    @Override
    public LoxodonWayfarer copy() {
        return new LoxodonWayfarer(this);
    }

}
