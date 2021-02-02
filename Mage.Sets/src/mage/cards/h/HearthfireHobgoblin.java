
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class HearthfireHobgoblin extends CardImpl {

    public HearthfireHobgoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R/W}{R/W}{R/W}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private HearthfireHobgoblin(final HearthfireHobgoblin card) {
        super(card);
    }

    @Override
    public HearthfireHobgoblin copy() {
        return new HearthfireHobgoblin(this);
    }
}
