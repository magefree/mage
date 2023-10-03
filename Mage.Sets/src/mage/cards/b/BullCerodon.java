

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class BullCerodon extends CardImpl {

    public BullCerodon (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{W}");
        this.subtype.add(SubType.BEAST);


        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private BullCerodon(final BullCerodon card) {
        super(card);
    }

    @Override
    public BullCerodon copy() {
        return new BullCerodon(this);
    }

}
