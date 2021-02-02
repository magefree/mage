
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class TrainedCaracal extends CardImpl {

    public TrainedCaracal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private TrainedCaracal(final TrainedCaracal card) {
        super(card);
    }

    @Override
    public TrainedCaracal copy() {
        return new TrainedCaracal(this);
    }
}
