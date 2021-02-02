
package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class DuskLegionDreadnought extends CardImpl {

    public DuskLegionDreadnought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private DuskLegionDreadnought(final DuskLegionDreadnought card) {
        super(card);
    }

    @Override
    public DuskLegionDreadnought copy() {
        return new DuskLegionDreadnought(this);
    }
}
