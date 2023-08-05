package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TheChainVeil extends CardImpl {

    public TheChainVeil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.supertype.add(SuperType.LEGENDARY);

        // At the beginning of your end step, if you didn't activate a loyalty ability of a planeswalker this turn, you lose 2 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new LoseLifeSourceControllerEffect(2), TargetController.YOU,
                TheChainVeilCondition.instance, false
        ), new ActivatedLoyaltyAbilityWatcher());

        // {4}, {T}: For each planeswalker you control, you may activate one of its loyalty abilities once this turn as though none of its loyalty abilities had been activated this turn.
        Ability ability = new SimpleActivatedAbility(
                new TheChainVeilIncreaseLoyaltyUseEffect(), new ManaCostsImpl<>("{4}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private TheChainVeil(final TheChainVeil card) {
        super(card);
    }

    @Override
    public TheChainVeil copy() {
        return new TheChainVeil(this);
    }
}

class ActivatedLoyaltyAbilityWatcher extends Watcher {

    private final Set<UUID> playerIds = new HashSet<>();

    public ActivatedLoyaltyAbilityWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATED_ABILITY) {
            return;
        }
        StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
        if (stackObject != null
                && stackObject.getStackAbility() != null
                && stackObject.getStackAbility() instanceof LoyaltyAbility) {
            playerIds.add(stackObject.getControllerId());
        }
    }

    @Override
    public void reset() {
        playerIds.clear();
    }

    public boolean activatedLoyaltyAbility(UUID playerId) {
        return playerIds.contains(playerId);
    }
}

class TheChainVeilIncreaseLoyaltyUseEffect extends ContinuousEffectImpl {

    public TheChainVeilIncreaseLoyaltyUseEffect() {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "For each planeswalker you control, you may activate one of its loyalty abilities once this turn as though none of its loyalty abilities have been activated this turn";
    }

    public TheChainVeilIncreaseLoyaltyUseEffect(final TheChainVeilIncreaseLoyaltyUseEffect effect) {
        super(effect);
    }

    @Override
    public TheChainVeilIncreaseLoyaltyUseEffect copy() {
        return new TheChainVeilIncreaseLoyaltyUseEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER,
                source.getControllerId(), source, game
        )) {
            permanent.incrementLoyaltyActivationsAvailable();
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

enum TheChainVeilCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ActivatedLoyaltyAbilityWatcher watcher = game.getState().getWatcher(ActivatedLoyaltyAbilityWatcher.class);
        return watcher != null && !watcher.activatedLoyaltyAbility(source.getControllerId());
    }

    @Override
    public String toString() {
        return "if you didn't activate a loyalty ability of a planeswalker this turn";
    }
}
