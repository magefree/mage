
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.abilities.keyword.AssistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class GamePlan extends CardImpl {

    public GamePlan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Assist
        this.addAbility(new AssistAbility());

        // Each player shuffles their hand and graveyard into their library, then draws seven cards. Exile Game Plan.
        this.getSpellAbility().addEffect(new ShuffleHandGraveyardAllEffect());
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private GamePlan(final GamePlan card) {
        super(card);
    }

    @Override
    public GamePlan copy() {
        return new GamePlan(this);
    }
}
