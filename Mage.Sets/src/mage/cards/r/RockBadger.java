
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RockBadger extends CardImpl {

    public RockBadger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.BADGER);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new MountainwalkAbility());
    }

    private RockBadger(final RockBadger card) {
        super(card);
    }

    @Override
    public RockBadger copy() {
        return new RockBadger(this);
    }
}
