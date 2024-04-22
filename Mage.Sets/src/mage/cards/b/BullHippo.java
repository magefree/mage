

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class BullHippo extends CardImpl {

    public BullHippo (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HIPPO);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new IslandwalkAbility());
    }

    private BullHippo(final BullHippo card) {
        super(card);
    }

    @Override
    public BullHippo copy() {
        return new BullHippo(this);
    }
}
