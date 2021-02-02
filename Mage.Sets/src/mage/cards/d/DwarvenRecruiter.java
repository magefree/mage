
package mage.cards.d;

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
 * @author LoneFox
 */
public final class DwarvenRecruiter extends CardImpl {

    private static final FilterCard filter = new FilterCard("Dwarf cards");

    static {
        filter.add(SubType.DWARF.getPredicate());
    }

    public DwarvenRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Dwarven Recruiter enters the battlefield, search your library for any number of Dwarf cards and reveal those cards. Shuffle your library, then put them on top of it in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RecruiterEffect(filter), false));
    }

    private DwarvenRecruiter(final DwarvenRecruiter card) {
        super(card);
    }

    @Override
    public DwarvenRecruiter copy() {
        return new DwarvenRecruiter(this);
    }
}
