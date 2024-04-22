

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class TangleMantis extends CardImpl {

    public TangleMantis (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.addAbility(TrampleAbility.getInstance());
    }

    private TangleMantis(final TangleMantis card) {
        super(card);
    }

    @Override
    public TangleMantis copy() {
        return new TangleMantis(this);
    }

}
