
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ShieldOfTheAges extends CardImpl {

    public ShieldOfTheAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}: Prevent the next 1 damage that would be dealt to you this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToControllerEffect(Duration.EndOfTurn, 1), new GenericManaCost(2)));
    }

    private ShieldOfTheAges(final ShieldOfTheAges card) {
        super(card);
    }

    @Override
    public ShieldOfTheAges copy() {
        return new ShieldOfTheAges(this);
    }
}
