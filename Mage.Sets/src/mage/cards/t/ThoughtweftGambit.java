
package mage.cards.t;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class ThoughtweftGambit extends CardImpl {

    public ThoughtweftGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{W/U}{W/U}");

        // Tap all creatures your opponents control and untap all creatures you control.
        this.getSpellAbility().addEffect(new ThoughtweftGambitEffect());

    }

    private ThoughtweftGambit(final ThoughtweftGambit card) {
        super(card);
    }

    @Override
    public ThoughtweftGambit copy() {
        return new ThoughtweftGambit(this);
    }
}

class ThoughtweftGambitEffect extends OneShotEffect {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public ThoughtweftGambitEffect() {
        super(Outcome.Benefit);
        staticText = "Tap all creatures your opponents control and untap all creatures you control";
    }

    private ThoughtweftGambitEffect(final ThoughtweftGambitEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean passed = false;
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        Player controller = game.getPlayer(source.getControllerId());
        if (opponents != null) {
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (opponents.contains(creature.getControllerId())) {
                    creature.tap(source, game);
                }
            }
            passed = true;
        }
        if (controller != null) {
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (controller.getId().equals(creature.getControllerId())) {
                    creature.untap(game);
                }
            }
            passed = true;
        }
        return passed;
    }

    @Override
    public ThoughtweftGambitEffect copy() {
        return new ThoughtweftGambitEffect(this);
    }
}
