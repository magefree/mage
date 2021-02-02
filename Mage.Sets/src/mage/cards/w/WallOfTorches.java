

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class WallOfTorches extends CardImpl {

    public WallOfTorches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
        this.addAbility(DefenderAbility.getInstance());
    }

    private WallOfTorches(final WallOfTorches card) {
        super(card);
    }

    @Override
    public WallOfTorches copy() {
        return new WallOfTorches(this);
    }

}
