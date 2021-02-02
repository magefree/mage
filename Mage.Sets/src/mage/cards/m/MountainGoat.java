
package mage.cards.m;

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
public final class MountainGoat extends CardImpl {

    public MountainGoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new MountainwalkAbility());
    }

    private MountainGoat(final MountainGoat card) {
        super(card);
    }

    @Override
    public MountainGoat copy() {
        return new MountainGoat(this);
    }
}
