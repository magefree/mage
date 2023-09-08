
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class DismalFailure extends CardImpl {

    public DismalFailure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        // Counter target spell. Its controller discards a card.
        this.getSpellAbility().addEffect(new DismalFailureEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private DismalFailure(final DismalFailure card) {
        super(card);
    }

    @Override
    public DismalFailure copy() {
        return new DismalFailure(this);
    }
}

class DismalFailureEffect extends OneShotEffect {

    public DismalFailureEffect() {
        super(Outcome.Neutral);
        this.staticText = "Counter target spell. Its controller discards a card";
    }

    private DismalFailureEffect(final DismalFailureEffect effect) {
        super(effect);
    }

    @Override
    public DismalFailureEffect copy() {
        return new DismalFailureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        Player controller = null;
        boolean countered = false;
        if (targetId != null) {
            controller = game.getPlayer(game.getControllerId(targetId));
        }
        if (targetId != null
                && game.getStack().counter(targetId, source, game)) {
            countered = true;
        }
        if (controller != null) {
            controller.discard(1, false, false, source, game);
        }
        return countered;
    }
}
