package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PromiseOfLoyalty extends CardImpl {

    public PromiseOfLoyalty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Each player puts a vow counter on a creature they control and sacrifices the rest. Each of those creatures can't attack you or planeswalkers you control for as long as it has a vow counter on it.
        this.getSpellAbility().addEffect(new PromiseOfLoyaltyEffect());
    }

    private PromiseOfLoyalty(final PromiseOfLoyalty card) {
        super(card);
    }

    @Override
    public PromiseOfLoyalty copy() {
        return new PromiseOfLoyalty(this);
    }
}

class PromiseOfLoyaltyEffect extends OneShotEffect {

    PromiseOfLoyaltyEffect() {
        super(Outcome.Benefit);
        staticText = "each player puts a vow counter on a creature they control and sacrifices the rest. " +
                "Each of those creatures can't attack you or planeswalkers you control for as long as it has a vow counter on it";
    }

    private PromiseOfLoyaltyEffect(final PromiseOfLoyaltyEffect effect) {
        super(effect);
    }

    @Override
    public PromiseOfLoyaltyEffect copy() {
        return new PromiseOfLoyaltyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || game.getBattlefield().count(
                    StaticFilters.FILTER_CONTROLLED_CREATURE,
                    playerId, source, game
            ) < 1) {
                continue;
            }
            TargetPermanent target = new TargetControlledCreaturePermanent();
            target.setNotTarget(true);
            player.choose(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                continue;
            }
            permanents.add(permanent);
            permanent.addCounters(CounterType.VOW.createInstance(), playerId, source, game);
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getSourceId(), game
        )) {
            if (permanent == null) {
                continue;
            }
            if (permanents.contains(permanent)) {
                game.addEffect(new PromiseOfLoyaltyAttackEffect().setTargetPointer(new FixedTarget(permanent, game)), source);
            } else {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}

class PromiseOfLoyaltyAttackEffect extends RestrictionEffect {

    public PromiseOfLoyaltyAttackEffect() {
        super(Duration.Custom);
    }

    public PromiseOfLoyaltyAttackEffect(final PromiseOfLoyaltyAttackEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent p = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (p == null || p.getCounters(game).getCount(CounterType.VOW) < 1) {
            discard();
            return false;
        }
        return permanent.getId().equals(p.getId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        if (source.isControlledBy(defenderId)) {
            return false;
        }
        Permanent planeswalker = game.getPermanent(defenderId);
        return planeswalker == null || !planeswalker.isControlledBy(source.getControllerId());
    }


    @Override
    public PromiseOfLoyaltyAttackEffect copy() {
        return new PromiseOfLoyaltyAttackEffect(this);
    }
}
