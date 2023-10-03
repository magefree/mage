

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BattleCryAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GoblinWardriver extends CardImpl {

    public GoblinWardriver (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new BattleCryAbility());
    }

    private GoblinWardriver(final GoblinWardriver card) {
        super(card);
    }

    @Override
    public GoblinWardriver copy() {
        return new GoblinWardriver(this);
    }

}
