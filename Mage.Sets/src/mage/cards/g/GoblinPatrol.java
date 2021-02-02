

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class GoblinPatrol extends CardImpl {

    public GoblinPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new EchoAbility("{R}"));
    }

    private GoblinPatrol(final GoblinPatrol card) {
        super(card);
    }

    @Override
    public GoblinPatrol copy() {
        return new GoblinPatrol(this);
    }

}