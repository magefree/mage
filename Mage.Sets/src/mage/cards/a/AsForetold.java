package mage.cards.a;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author stravant
 *
 * Note, this card is pretty hacky in its implementation due to the fact that
 * the alternative cost system doesn't really support "once each turn"
 * alternative costs in an obvious way, but it should work in all scenarios as
 * far as I can see.
 */
public final class AsForetold extends CardImpl {

    public AsForetold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of your upkeep, put a time counter on As Foretold.
        addAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new AddCountersSourceEffect(
                                CounterType.TIME.createInstance(),
                                new StaticValue(1),
                                true),
                        TargetController.YOU,
                        /* optional = */ false));

        // Once each turn, you may pay {0} rather than pay the mana cost for a spell you cast with converted mana cost X or less, where X is the number of time counters on As Foretold.
        addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new AsForetoldAddAltCostEffect()),
                new AsForetoldAltCostUsedWatcher());

    }

    public AsForetold(final AsForetold card) {
        super(card);
    }

    @Override
    public AsForetold copy() {
        return new AsForetold(this);
    }
}

/**
 * Used to determine what cast objects to apply the alternative cost to
 */
class SpellWithManaCostLessThanOrEqualToCondition implements Condition {

    private int counters;

    public SpellWithManaCostLessThanOrEqualToCondition(int counters) {
        this.counters = counters;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        return object != null
                && !object.isLand()
                && object.getConvertedManaCost() <= counters;
    }
}

/**
 * Special AlternativeCostSourceAbility implementation. We wrap the call to
 * askToActivateAlternativeCosts in order to tell when the alternative cost is
 * used, and mark it as having been used this turn in the watcher
 */
class AsForetoldAlternativeCost extends AlternativeCostSourceAbility {

    private UUID sourceAsForetold;
    private boolean wasActivated;

    AsForetoldAlternativeCost(UUID sourceAsForetold, int timeCounters) {
        super(new ManaCostsImpl("{0}"), new SpellWithManaCostLessThanOrEqualToCondition(timeCounters));
        this.sourceAsForetold = sourceAsForetold;
    }

   private AsForetoldAlternativeCost(final AsForetoldAlternativeCost ability) {
        super(ability);
        this.sourceAsForetold = ability.sourceAsForetold;
        this.wasActivated = ability.wasActivated;
    }

    @Override
    public AsForetoldAlternativeCost copy() {
        return new AsForetoldAlternativeCost(this);
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        Permanent asForetold = game.getPermanent(sourceAsForetold);
        if (controller != null
                && asForetold != null) {
            if (controller.chooseUse(Outcome.Neutral, "Do you wish to use " + asForetold.getLogName() + " to pay the alternative cost ?", ability, game)) {
                wasActivated = super.askToActivateAlternativeCosts(ability, game);
                if (wasActivated) {
                    // Get the watcher
                    AsForetoldAltCostUsedWatcher asForetoldAltCostUsedWatcher
                            = game.getState().getWatcher(AsForetoldAltCostUsedWatcher.class, sourceAsForetold);

                    // Mark as used
                    asForetoldAltCostUsedWatcher.markUsedThisTurn();
                }
            }
        }
        return wasActivated;
    }

}

/**
 * The continuous effect that adds the option to pay the alternative cost if we
 * haven't used it yet this turn
 */
class AsForetoldAddAltCostEffect extends ContinuousEffectImpl {

    public AsForetoldAddAltCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Once each turn, you may pay {0} rather than pay the mana cost for a spell you cast with converted mana cost X or less, where X is the number of time counters on {this}.";
    }

    public AsForetoldAddAltCostEffect(final AsForetoldAddAltCostEffect effect) {
        super(effect);
    }

    @Override
    public AsForetoldAddAltCostEffect copy() {
        return new AsForetoldAddAltCostEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                // Get the watcher
                AsForetoldAltCostUsedWatcher asForetoldAltCostUsedWatcher
                        = game.getState().getWatcher(
                                AsForetoldAltCostUsedWatcher.class, sourcePermanent.getId());

                // If we haven't used it yet this turn, give the option of using the zero alternative cost
                if (!asForetoldAltCostUsedWatcher.hasBeenUsedThisTurn()) {
                    int timeCounters = sourcePermanent.getCounters(game).getCount("time");
                    controller.getAlternativeSourceCosts().add(new AsForetoldAlternativeCost(sourcePermanent.getId(), timeCounters));
                }

                // Return true even if we didn't add the alt cost. We still applied the effect
                return true;
            }
        }
        return false;
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

/**
 * Watcher used as extra storage to record whether a given As Foretold has been
 * used this turn. Technically speaking this watcher doesn't *watch* any
 * GameEvents, but it does "watch" the alternative cost being used. That just
 * isn't possible to watch through a game event. It's still helpful to co-op the
 * Watcher system for this since it automatically handles ZoneChangeCounter
 * stuff and resetting the condition at the end of the turn.
 */
class AsForetoldAltCostUsedWatcher extends Watcher {

    public AsForetoldAltCostUsedWatcher() {
        super("asForetoldAltCostUsedWatcher", WatcherScope.CARD);
    }

    public AsForetoldAltCostUsedWatcher(final AsForetoldAltCostUsedWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        // Nothing to do, we explicitly mark used in the alternative cost
    }

    public boolean hasBeenUsedThisTurn() {
        return conditionMet();
    }

    public void markUsedThisTurn() {
        condition = true;
    }

    @Override
    public AsForetoldAltCostUsedWatcher copy() {
        return new AsForetoldAltCostUsedWatcher(this);
    }
}
