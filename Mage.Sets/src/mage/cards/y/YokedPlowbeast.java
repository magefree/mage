

package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class YokedPlowbeast extends CardImpl {

    public YokedPlowbeast (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    public YokedPlowbeast (final YokedPlowbeast card) {
        super(card);
    }

    @Override
    public YokedPlowbeast copy() {
        return new YokedPlowbeast(this);
    }

}
