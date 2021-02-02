
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class Respite extends CardImpl {

    public Respite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Prevent all combat damage that would be dealt this turn. 
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
        
        // You gain 1 life for each attacking creature.
        this.getSpellAbility().addEffect(new GainLifeEffect(new AttackingCreatureCount()));
    }

    private Respite(final Respite card) {
        super(card);
    }

    @Override
    public Respite copy() {
        return new Respite(this);
    }
}
