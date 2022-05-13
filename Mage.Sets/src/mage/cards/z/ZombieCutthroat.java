
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class ZombieCutthroat extends CardImpl {

    public ZombieCutthroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Morph-Pay 5 life.
        this.addAbility(new MorphAbility(new PayLifeCost(5)));
    }

    private ZombieCutthroat(final ZombieCutthroat card) {
        super(card);
    }

    @Override
    public ZombieCutthroat copy() {
        return new ZombieCutthroat(this);
    }
}
