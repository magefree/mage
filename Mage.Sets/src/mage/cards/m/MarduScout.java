
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class MarduScout extends CardImpl {

    public MarduScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Dash {1}{R}
        this.addAbility(new DashAbility("{1}{R}"));
    }

    private MarduScout(final MarduScout card) {
        super(card);
    }

    @Override
    public MarduScout copy() {
        return new MarduScout(this);
    }
}
