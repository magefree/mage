
package mage.cards.v;

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
 * @author wetterlicht
 */
public final class Vex extends CardImpl {

    public Vex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Counter target spell. That spell's controller may draw a card.
        getSpellAbility().addTarget(new TargetSpell());
        getSpellAbility().addEffect(new VexEffect());
    }

    private Vex(final Vex card) {
        super(card);
    }

    @Override
    public Vex copy() {
        return new Vex(this);
    }
}

class VexEffect extends OneShotEffect {

    public VexEffect() {
        super(Outcome.Neutral);
        this.staticText = "Counter target spell. That spell's controller may draw a card";
    }

    public VexEffect(final VexEffect effect) {
        super(effect);
    }

    @Override
    public VexEffect copy() {
        return new VexEffect(this);
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
