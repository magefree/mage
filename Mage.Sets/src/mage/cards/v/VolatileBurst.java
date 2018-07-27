package mage.cards.v;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.permanent.DamagedPlayerThisTurnPredicate;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author NinthWorld
 */
public final class VolatileBurst extends CardImpl {

    private static final FilterCreaturePermanent filterDealtDamage = new FilterCreaturePermanent("creature that was dealt damage this turn");
    private static final FilterPlayer filterLostLife = new FilterPlayer("player who lost life this turn");

    static {
        filterDealtDamage.add(new WasDealtDamageThisTurnPredicate());
        filterLostLife.add(new PlayerLostLifePredicate());
    }

    public VolatileBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");
        

        // Choose one -
        //   Volatile Burst deals 4 damage to target creature that was dealt damage this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterDealtDamage));

        //   Target player who lost life this turn loses 4 life.
        Mode mode = new Mode();
        mode.getEffects().add(new LoseLifeTargetEffect(4));
        mode.getTargets().add(new TargetPlayer(1, 1, false, filterLostLife));
        this.getSpellAbility().addMode(mode);
    }

    public VolatileBurst(final VolatileBurst card) {
        super(card);
    }

    @Override
    public VolatileBurst copy() {
        return new VolatileBurst(this);
    }
}

// From RixMaadiGuildmage
class PlayerLostLifePredicate implements Predicate<Player> {

    public PlayerLostLifePredicate() {

    }

    @Override
    public boolean apply(Player input, Game game) {
        PlayerLostLifeWatcher watcher = (PlayerLostLifeWatcher) game.getState().getWatchers().get(PlayerLostLifeWatcher.class.getSimpleName());
        if (watcher != null) {
            return (0 < watcher.getLiveLost(input.getId()));
        }
        return false;
    }

    @Override
    public String toString() {
        return "Player lost life";
    }
}