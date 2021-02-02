
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author JayDi85
 */
public final class StormFleetSprinter extends CardImpl {

    public StormFleetSprinter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Storm Fleet Sprinter can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private StormFleetSprinter(final StormFleetSprinter card) {
        super(card);
    }

    @Override
    public StormFleetSprinter copy() {
        return new StormFleetSprinter(this);
    }
}