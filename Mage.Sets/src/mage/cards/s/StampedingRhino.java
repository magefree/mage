

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class StampedingRhino extends CardImpl {

    public StampedingRhino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.RHINO);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(TrampleAbility.getInstance());
    }

    private StampedingRhino(final StampedingRhino card) {
        super(card);
    }

    @Override
    public StampedingRhino copy() {
        return new StampedingRhino(this);
    }

}
