
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.watchers.Watcher;
import mage.watchers.common.MorbidWatcher;

/**
 * @author nantuko
 */
public final class BrimstoneVolley extends CardImpl {

    public BrimstoneVolley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Brimstone Volley deals 3 damage to any target.
        // <i>Morbid</i> &mdash; Brimstone Volley deals 5 damage to that creature or player instead if a creature died this turn.
        this.getSpellAbility().addEffect(new BrimstoneVolleyEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public BrimstoneVolley(final BrimstoneVolley card) {
        super(card);
    }

    @Override
    public BrimstoneVolley copy() {
        return new BrimstoneVolley(this);
    }
}

class BrimstoneVolleyEffect extends OneShotEffect {

    public BrimstoneVolleyEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 3 damage to any target.\n <i>Morbid</i> &mdash; {this} deals 5 damage to that permanent or player instead if a creature died this turn";
    }

    public BrimstoneVolleyEffect(final BrimstoneVolleyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 3;
        Watcher watcher = game.getState().getWatchers().get(MorbidWatcher.class.getSimpleName());
        if (watcher.conditionMet()) {
            damage = 5;
        }
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            permanent.damage(damage, source.getSourceId(), game, false, true);
            return true;
        }
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.damage(damage, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public BrimstoneVolleyEffect copy() {
        return new BrimstoneVolleyEffect(this);
    }

}
