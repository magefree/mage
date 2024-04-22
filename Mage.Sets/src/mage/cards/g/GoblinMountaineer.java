

package mage.cards.g;

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
public final class GoblinMountaineer extends CardImpl {

    public GoblinMountaineer (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new MountainwalkAbility());
    }

    private GoblinMountaineer(final GoblinMountaineer card) {
        super(card);
    }

    @Override
    public GoblinMountaineer copy() {
        return new GoblinMountaineer(this);
    }
}
