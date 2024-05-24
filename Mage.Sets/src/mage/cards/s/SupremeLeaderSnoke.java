package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author NinthWorld
 */
public final class SupremeLeaderSnoke extends CardImpl {

    public SupremeLeaderSnoke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNOKE);
        this.setStartingLoyalty(3);

        // +1: Put a loyalty counter on Supreme Leader Snoke for each life lost by all opponents from noncombat sources this turn.
        Ability ability1 = new LoyaltyAbility(new SupremeLeaderSnokeCounterEffect(CounterType.LOYALTY.createInstance()), 1);
        this.addAbility(ability1);

        // -1: Draw a card and lose 1 life.
        Ability ability2 = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), -1);
        ability2.addEffect(new LoseLifeSourceControllerEffect(1).setText("and lose 1 life"));
        this.addAbility(ability2);

        // -X: Gain control of target creature with converted mana cost X. Untap that creature. It gains haste. Sacrifice that creature at the beginning of the next end step.
        Ability ability3 = new LoyaltyAbility(new GainControlTargetEffect(Duration.WhileOnBattlefield)
                .setText("Gain control of target creature with mana value X"));
        ability3.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        ability3.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield).setText("It gains haste"));
        ability3.addEffect(new GainAbilityTargetEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect()), Duration.WhileOnBattlefield)
                .setText("Sacrifice that creature at the beginning of the next end step"));
        ability3.setTargetAdjuster(new XManaValueTargetAdjuster());
        ability3.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE));
        this.addAbility(ability3);
    }

    private SupremeLeaderSnoke(final SupremeLeaderSnoke card) {
        super(card);
    }

    @Override
    public SupremeLeaderSnoke copy() {
        return new SupremeLeaderSnoke(this);
    }
}

enum OpponentNoncombatLostLifeCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        PlayerLostLifeNonCombatWatcher watcher = game.getState().getWatcher(PlayerLostLifeNonCombatWatcher.class);
        if (watcher != null) {
            return watcher.getAllOppLifeLost(sourceAbility.getControllerId(), game);
        }
        return 0;
    }

    @Override
    public OpponentNoncombatLostLifeCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "life lost by all opponents from noncombat sources this turn";
    }
}

class SupremeLeaderSnokeCounterEffect extends OneShotEffect {

    protected final Counter counter;

    public SupremeLeaderSnokeCounterEffect(Counter counter) {
        super(Outcome.Benefit);
        this.counter = counter;
        staticText = "Put a loyalty counter on {this} for each life lost by all opponents from noncombat sources this turn";
    }

    private SupremeLeaderSnokeCounterEffect(final SupremeLeaderSnokeCounterEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            int amount = OpponentNoncombatLostLifeCount.instance.calculate(game, source, this);
            if (amount > 0) {
                Counter counterToAdd = counter.copy();
                counterToAdd.add(amount - counter.getCount());
                permanent.addCounters(counterToAdd, source.getControllerId(), source, game);
            }
        }
        return true;
    }

    @Override
    public SupremeLeaderSnokeCounterEffect copy() {
        return new SupremeLeaderSnokeCounterEffect(this);
    }
}

class PlayerLostLifeNonCombatWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfLifeLostThisTurn = new HashMap<>();

    PlayerLostLifeNonCombatWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        // non combat lose life
        if (event.getType() == GameEvent.EventType.LOST_LIFE && !event.getFlag()) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = amountOfLifeLostThisTurn.get(playerId);
                if (amount == null) {
                    amount = event.getAmount();
                } else {
                    amount = amount + event.getAmount();
                }
                amountOfLifeLostThisTurn.put(playerId, amount);
            }
        }
    }

    public int getAllOppLifeLost(UUID playerId, Game game) {
        int amount = 0;
        for (UUID opponentId : this.amountOfLifeLostThisTurn.keySet()) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null && opponent.hasOpponent(playerId, game)) {
                amount += this.amountOfLifeLostThisTurn.getOrDefault(opponentId, 0);
            }
        }
        return amount;
    }

    @Override
    public void reset() {
        super.reset();
        amountOfLifeLostThisTurn.clear();
    }
}
