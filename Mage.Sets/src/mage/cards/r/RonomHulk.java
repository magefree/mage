
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;

/**
 *
 * @author LoneFox
 */
public final class RonomHulk extends CardImpl {

    private static final FilterCard filter = new FilterCard("snow");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public RonomHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Protection from snow
        this.addAbility(new ProtectionAbility(filter));
        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));
    }

    private RonomHulk(final RonomHulk card) {
        super(card);
    }

    @Override
    public RonomHulk copy() {
        return new RonomHulk(this);
    }
}
