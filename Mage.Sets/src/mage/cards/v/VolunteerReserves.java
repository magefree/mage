

package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class VolunteerReserves extends CardImpl {

    public VolunteerReserves (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Banding
        this.addAbility(BandingAbility.getInstance());

        // Cumulative upkeep-Pay {1}.
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));
    }

    public VolunteerReserves (final VolunteerReserves card) {
        super(card);
    }

    @Override
    public VolunteerReserves copy() {
        return new VolunteerReserves(this);
    }

}
