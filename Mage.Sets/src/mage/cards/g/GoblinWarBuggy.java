

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class GoblinWarBuggy extends CardImpl {

    public GoblinWarBuggy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new EchoAbility("{1}{R}"));
    }

    private GoblinWarBuggy(final GoblinWarBuggy card) {
        super(card);
    }

    @Override
    public GoblinWarBuggy copy() {
        return new GoblinWarBuggy(this);
    }

}