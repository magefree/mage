

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class CliffThreader extends CardImpl {

    public CliffThreader (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(new MountainwalkAbility());
    }

    private CliffThreader(final CliffThreader card) {
        super(card);
    }

    @Override
    public CliffThreader copy() {
        return new CliffThreader(this);
    }
}
