
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class GoblinDeathraiders extends CardImpl {

    public GoblinDeathraiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(TrampleAbility.getInstance());
    }

    private GoblinDeathraiders(final GoblinDeathraiders card) {
        super(card);
    }

    @Override
    public GoblinDeathraiders copy() {
        return new GoblinDeathraiders(this);
    }
}
