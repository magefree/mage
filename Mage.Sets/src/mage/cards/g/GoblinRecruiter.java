
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.RecruiterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;


/**
 *
 * @author Quercitron
 */
public final class GoblinRecruiter extends CardImpl {

    private static final FilterCard filter = new FilterCard("Goblin cards");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public GoblinRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Goblin Recruiter enters the battlefield, search your library for any number of Goblin cards and reveal those cards. Shuffle your library, then put them on top of it in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RecruiterEffect(filter), false));
    }

    private GoblinRecruiter(final GoblinRecruiter card) {
        super(card);
    }

    @Override
    public GoblinRecruiter copy() {
        return new GoblinRecruiter(this);
    }
}
