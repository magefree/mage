
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class SleekSchooner extends CardImpl {

    public SleekSchooner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Crew 1
        this.addAbility(new CrewAbility(1));

    }

    private SleekSchooner(final SleekSchooner card) {
        super(card);
    }

    @Override
    public SleekSchooner copy() {
        return new SleekSchooner(this);
    }
}
