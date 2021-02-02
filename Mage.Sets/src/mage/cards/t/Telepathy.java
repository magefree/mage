
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.PlayWithHandRevealedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class Telepathy extends CardImpl {

    public Telepathy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");

        // Your opponents play with their hands revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithHandRevealedEffect(TargetController.OPPONENT)));
    }

    private Telepathy(final Telepathy card) {
        super(card);
    }

    @Override
    public Telepathy copy() {
        return new Telepathy(this);
    }
}
