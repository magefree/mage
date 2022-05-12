
package mage.cards.s;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class Simoon extends CardImpl {

    public Simoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{G}");

        // Simoon deals 1 damage to each creature target opponent controls.
        this.getSpellAbility().addEffect(new SimoonEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Simoon(final Simoon card) {
        super(card);
    }

    @Override
    public Simoon copy() {
        return new Simoon(this);
    }
}

class SimoonEffect extends OneShotEffect {

    public SimoonEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to each creature target opponent controls";
    }

    public SimoonEffect(final SimoonEffect effect) {
        super(effect);
    }

    @Override
    public SimoonEffect copy() {
        return new SimoonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(player.getId()));
            List<Permanent> creatures = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
            for (Permanent creature : creatures) {
                creature.damage(1, source.getSourceId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }
}