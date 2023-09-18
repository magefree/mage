
package mage.cards.s;

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
public final class ScreamreachBrawler extends CardImpl {

    public ScreamreachBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Dash {1}{R}
        this.addAbility(new DashAbility("{1}{R}"));
    }

    private ScreamreachBrawler(final ScreamreachBrawler card) {
        super(card);
    }

    @Override
    public ScreamreachBrawler copy() {
        return new ScreamreachBrawler(this);
    }
}
