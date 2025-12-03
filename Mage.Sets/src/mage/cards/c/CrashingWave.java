package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.common.WaterbendXCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPermanentAmount;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrashingWave extends CardImpl {

    public CrashingWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}");

        // As an additional cost to cast this spell, waterbend {X}.
        this.getSpellAbility().addCost(new WaterbendXCost());

        // Tap up to X target creatures, then distribute three stun counters among tapped creatures your opponents control.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap up to X target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellAbility().addEffect(new CrashingWaveEffect());
    }

    private CrashingWave(final CrashingWave card) {
        super(card);
    }

    @Override
    public CrashingWave copy() {
        return new CrashingWave(this);
    }
}

class CrashingWaveEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("tapped creatures your opponents control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    CrashingWaveEffect() {
        super(Outcome.Benefit);
        staticText = ", then distribute three stun counters among tapped creatures your opponents control";
    }

    private CrashingWaveEffect(final CrashingWaveEffect effect) {
        super(effect);
    }

    @Override
    public CrashingWaveEffect copy() {
        return new CrashingWaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(filter, source.getControllerId(), source, game, 1)) {
            return false;
        }
        TargetPermanentAmount target = new TargetPermanentAmount(3, 1, filter);
        target.withNotTarget(true);
        player.chooseTarget(outcome, target, source, game);
        for (UUID targetId : target.getTargets()) {
            Optional.ofNullable(targetId)
                    .map(game::getPermanent)
                    .ifPresent(permanent -> permanent.addCounters(
                            CounterType.STUN.createInstance(target.getTargetAmount(targetId)), source, game
                    ));
        }
        return true;
    }
}
