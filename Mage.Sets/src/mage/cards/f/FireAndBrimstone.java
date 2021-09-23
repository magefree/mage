package mage.cards.f;

import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPlayer;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author L_J
 */
public final class FireAndBrimstone extends CardImpl {

    private static final FilterPlayer filter = new FilterPlayer("player who attacked this turn");

    static {
        filter.add(FireAndBrimstonePredicate.instance);
    }

    public FireAndBrimstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}{W}");

        // Fire and Brimstone deals 4 damage to target player who attacked this turn and 4 damage to you.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new DamageControllerEffect(4).setText("and 4 damage to you"));
        this.getSpellAbility().addTarget(new TargetPlayer(1, 1, false, filter));
    }

    private FireAndBrimstone(final FireAndBrimstone card) {
        super(card);
    }

    @Override
    public FireAndBrimstone copy() {
        return new FireAndBrimstone(this);
    }
}

enum FireAndBrimstonePredicate implements ObjectSourcePlayerPredicate<Player> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player player = input.getObject();
        UUID playerId = input.getPlayerId();
        if (player == null || playerId == null) {
            return false;
        }
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        return watcher != null
                && !watcher.getAttackedThisTurnCreatures().isEmpty()
                && player.getId().equals(game.getActivePlayerId());
    }
}
