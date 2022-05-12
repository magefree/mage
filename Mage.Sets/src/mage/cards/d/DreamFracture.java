
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public final class DreamFracture extends CardImpl {

    public DreamFracture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // Counter target spell. Its controller draws a card.
        this.getSpellAbility().addEffect(new DreamFractureEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));

    }

    private DreamFracture(final DreamFracture card) {
        super(card);
    }

    @Override
    public DreamFracture copy() {
        return new DreamFracture(this);
    }
}

class DreamFractureEffect extends OneShotEffect {

    public DreamFractureEffect() {
        super(Outcome.Neutral);
        this.staticText = "Counter target spell. Its controller draws a card";
    }

    public DreamFractureEffect(final DreamFractureEffect effect) {
        super(effect);
    }

    @Override
    public DreamFractureEffect copy() {
        return new DreamFractureEffect(this);
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
            controller.drawCards(1, source, game);
        }
        return countered;
    }
}
