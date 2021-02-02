
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ToweringIndrik extends CardImpl {

    public ToweringIndrik(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private ToweringIndrik(final ToweringIndrik card) {
        super(card);
    }

    @Override
    public ToweringIndrik copy() {
        return new ToweringIndrik(this);
    }
}
