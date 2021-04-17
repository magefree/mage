
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class TimeReversal extends CardImpl {

    public TimeReversal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Each player shuffles their hand and graveyard into their library, then draws seven cards
        this.getSpellAbility().addEffect(new ShuffleHandGraveyardAllEffect());
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private TimeReversal(final TimeReversal card) {
        super(card);
    }

    @Override
    public TimeReversal copy() {
        return new TimeReversal(this);
    }
}
