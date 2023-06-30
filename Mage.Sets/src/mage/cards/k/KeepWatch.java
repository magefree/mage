
package mage.cards.k;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jeffwadsworth
 */
public final class KeepWatch extends CardImpl {

    public KeepWatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");


        // Draw a card for each attacking creature.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new AttackingCreatureCount()).setText("draw a card for each attacking creature"));
    }

    private KeepWatch(final KeepWatch card) {
        super(card);
    }

    @Override
    public KeepWatch copy() {
        return new KeepWatch(this);
    }
}
