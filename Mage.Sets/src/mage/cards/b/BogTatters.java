

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class BogTatters extends CardImpl {

    public BogTatters (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.WRAITH);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);
        this.addAbility(new SwampwalkAbility());
    }

    private BogTatters(final BogTatters card) {
        super(card);
    }

    @Override
    public BogTatters copy() {
        return new BogTatters(this);
    }

}
