
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class HobgoblinDragoon extends CardImpl {

    public HobgoblinDragoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R/W}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private HobgoblinDragoon(final HobgoblinDragoon card) {
        super(card);
    }

    @Override
    public HobgoblinDragoon copy() {
        return new HobgoblinDragoon(this);
    }
}
