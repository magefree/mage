
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author fireshoes
 */
public final class SpiritCairn extends CardImpl {

    public SpiritCairn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // Whenever a player discards a card, you may pay {W}. If you do, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new SimpleTriggeredAbility(Zone.BATTLEFIELD, GameEvent.EventType.DISCARDED_CARD, 
                new DoIfCostPaid(new CreateTokenEffect(new SpiritWhiteToken()), new ManaCostsImpl("{W}")), 
                "Whenever a player discards a card, ", 
                false, false));
    }

    public SpiritCairn(final SpiritCairn card) {
        super(card);
    }

    @Override
    public SpiritCairn copy() {
        return new SpiritCairn(this);
    }
}
