
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class Fumigate extends CardImpl {

    public Fumigate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Destroy all creatures. You gain 1 life for each creature destroyed this way.
        this.getSpellAbility().addEffect(new FumigateEffect());
    }

    private Fumigate(final Fumigate card) {
        super(card);
    }

    @Override
    public Fumigate copy() {
        return new Fumigate(this);
    }
}

class FumigateEffect extends OneShotEffect {

    public FumigateEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures. You gain 1 life for each creature destroyed this way";
    }

    public FumigateEffect(final FumigateEffect effect) {
        super(effect);
    }

    @Override
    public FumigateEffect copy() {
        return new FumigateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int destroyedCreature = 0;
            for (Permanent creature : game.getState().getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game)) {
                if (creature.destroy(source, game, false)) {
                    destroyedCreature++;
                }
            }
            if (destroyedCreature > 0) {
                game.getState().processAction(game);
                controller.gainLife(destroyedCreature, game, source);
            }
            return true;
        }
        return false;
    }
}
