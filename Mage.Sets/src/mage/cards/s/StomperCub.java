

package mage.cards.s;

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
public final class StomperCub extends CardImpl {

    public StomperCub (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);
        this.addAbility(TrampleAbility.getInstance());
    }

    private StomperCub(final StomperCub card) {
        super(card);
    }

    @Override
    public StomperCub copy() {
        return new StomperCub(this);
    }

}
