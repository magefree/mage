
package mage.cards.w;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class WorryBeads extends CardImpl {

    public WorryBeads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of each player's upkeep, that player puts the top card of their library into their graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new PutLibraryIntoGraveTargetEffect(1).setText("that player puts the top card of their library into their graveyard"),
                TargetController.ANY, false, true));
    }

    public WorryBeads(final WorryBeads card) {
        super(card);
    }

    @Override
    public WorryBeads copy() {
        return new WorryBeads(this);
    }
}
