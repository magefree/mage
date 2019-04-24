
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPlayer;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.other.PlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author L_J
 */
public final class FireAndBrimstone extends CardImpl {

     private static final FilterPlayer filter = new FilterPlayer("player who attacked this turn");

     static {
        filter.add(new FireAndBrimstonePredicate());
    }

    public FireAndBrimstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}{W}");

        // Fire and Brimstone deals 4 damage to target player who attacked this turn and 4 damage to you.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new DamageControllerEffect(4).setText("and 4 damage to you"));
        this.getSpellAbility().addTarget(new TargetPlayer(1, 1, false, filter));
    }

    public FireAndBrimstone(final FireAndBrimstone card) {
        super(card);
    }

    @Override
    public FireAndBrimstone copy() {
        return new FireAndBrimstone(this);
    }
}

class FireAndBrimstonePredicate extends PlayerPredicate {

    public FireAndBrimstonePredicate() {
        super(null);
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player player = input.getObject();
        UUID playerId = input.getPlayerId();
        if (player == null || playerId == null) {
            return false;
        }
        AttackedThisTurnWatcher watcher = (AttackedThisTurnWatcher) game.getState().getWatchers().get(AttackedThisTurnWatcher.class.getSimpleName());
        if (watcher != null) {
            if (!watcher.getAttackedThisTurnCreatures().isEmpty()) {
                return player.getId().equals(game.getActivePlayerId());
            }
        }
        return false;
    }
}
