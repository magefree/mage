package mage.cards.s;

import java.util.Iterator;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerLostLifeNonCombatWatcher;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author NinthWorld
 */
public final class SupremeLeaderSnoke extends CardImpl {

    UUID ability3Id;

    public SupremeLeaderSnoke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{U}{B}{R}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNOKE);
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Put a loyalty counter on Supreme Leader Snoke for each life lost by all opponents from noncombat sources this turn.
        Ability ability1 = new LoyaltyAbility(new SupremeLeaderSnokeCounterEffect(CounterType.LOYALTY.createInstance()), 1);
        this.addAbility(ability1);

        // -1: Draw a card and lose 1 life.
        Ability ability2 = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), -1);
        ability2.addEffect(new LoseLifeSourceControllerEffect(1).setText("and lose 1 life"));
        this.addAbility(ability2);

        // -X: Gain control of target creature with converted mana cost X. Untap that creature. It gains haste. Sacrifice that creature at the beginning of the next end step.
        Ability ability3 = new LoyaltyAbility(new GainControlTargetEffect(Duration.WhileOnBattlefield)
            .setText("Gain control of target creature with converted mana cost X"));
        ability3.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        ability3.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield).setText("It gains haste"));
        ability3.addEffect(new GainAbilityTargetEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect()), Duration.WhileOnBattlefield)
            .setText("Sacrifice that creature at the beginning of the next end step"));
        ability3Id = ability3.getOriginalId();
        ability3.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability3);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(ability3Id)) {
            int cmc = 0;
            for (Cost cost : ability.getCosts()) {
                if (cost instanceof PayVariableLoyaltyCost) {
                    cmc = ((PayVariableLoyaltyCost) cost).getAmount();
                }
            }
            FilterCreaturePermanent newFilter = StaticFilters.FILTER_PERMANENT_CREATURE.copy();
            newFilter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, cmc));
            ability.getTargets().clear();
            ability.addTarget(new TargetCreaturePermanent(newFilter));
        }
    }

    public SupremeLeaderSnoke(final SupremeLeaderSnoke card) {
        super(card);
        this.ability3Id = card.ability3Id;
    }

    @Override
    public SupremeLeaderSnoke copy() {
        return new SupremeLeaderSnoke(this);
    }
}

class OpponentNoncombatLostLifeCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        PlayerLostLifeNonCombatWatcher watcher = (PlayerLostLifeNonCombatWatcher) game.getState().getWatchers().get(PlayerLostLifeNonCombatWatcher.class.getSimpleName());
        if(watcher != null) {
            return watcher.getAllOppLifeLost(source.getControllerId(), game);
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new OpponentNoncombatLostLifeCount();
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

    public SupremeLeaderSnokeCounterEffect(final SupremeLeaderSnokeCounterEffect effect) {
        super(effect);
        this.counter = effect.counter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if(permanent != null) {
            int amount = new OpponentNoncombatLostLifeCount().calculate(game, source, this);
            if(amount > 0) {
                Counter counterToAdd = counter.copy();
                counterToAdd.add(amount - counter.getCount());
                permanent.addCounters(counterToAdd, source, game);
            }
        }
        return true;
    }

    @Override
    public SupremeLeaderSnokeCounterEffect copy() {
        return new SupremeLeaderSnokeCounterEffect(this);
    }
}