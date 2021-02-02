
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class HulkingGoblin extends CardImpl {

    public HulkingGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Hulking Goblin can't block.
        this.addAbility(new CantBlockAbility());
    }

    private HulkingGoblin(final HulkingGoblin card) {
        super(card);
    }

    @Override
    public HulkingGoblin copy() {
        return new HulkingGoblin(this);
    }
}
