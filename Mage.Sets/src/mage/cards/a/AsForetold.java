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
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author stravant
 *
 */
public final class AsForetold extends CardImpl {

    public AsForetold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of your upkeep, put a time counter on As Foretold.
        addAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new AddCountersSourceEffect(
                                CounterType.TIME.createInstance(),
                                StaticValue.get(1),
                                true),
                        TargetController.YOU,
                        false));

        // Once each turn, you may pay {0} rather than pay the mana cost for a spell you cast with converted mana cost X or less, where X is the number of time counters on As Foretold.
        addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new AsForetoldAddAltCostEffect()));

    }

    private AsForetold(final AsForetold card) {
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
        MageObject object = game.getObject(source);
        return object != null
                && !object.isLand(game)
                && object.getManaValue() <= counters;
    }
}

/**
 * Special AlternativeCostSourceAbility implementation. We wrap the call to
 * askToActivateAlternativeCosts in order to tell when the alternative cost is
 * used, and mark it as having been used this turn in the watcher
 */
class AsForetoldAlternativeCost extends AlternativeCostSourceAbility {

    private boolean wasActivated;

    AsForetoldAlternativeCost(int timeCounters) {
        super(new ManaCostsImpl("{0}"), new SpellWithManaCostLessThanOrEqualToCondition(timeCounters));
    }

    private AsForetoldAlternativeCost(final AsForetoldAlternativeCost ability) {
        super(ability);
        this.wasActivated = ability.wasActivated;
    }

    @Override
    public AsForetoldAlternativeCost copy() {
        return new AsForetoldAlternativeCost(this);
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        Permanent asForetold = game.getPermanent(getSourceId());
        if (controller != null
                && asForetold != null) {
            if (controller.chooseUse(Outcome.Neutral, "Use "
                    + asForetold.getLogName() + " to pay the alternative cost ?", ability, game)) {
                wasActivated = super.askToActivateAlternativeCosts(ability, game);
                if (wasActivated) {
                    game.getState().setValue(asForetold.getId().toString()
                            + asForetold.getZoneChangeCounter(game)
                            + asForetold.getTurnsOnBattlefield(), true);
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
        staticText = "Once each turn, you may pay {0} rather than pay the mana cost for a spell you cast with mana value X or less, where X is the number of time counters on {this}.";
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
                Boolean wasItUsed = (Boolean) game.getState().getValue(
                        sourcePermanent.getId().toString()
                        + sourcePermanent.getZoneChangeCounter(game)
                        + sourcePermanent.getTurnsOnBattlefield());
                // If we haven't used it yet this turn, give the option of using the zero alternative cost
                if (wasItUsed == null) {
                    int timeCounters = sourcePermanent.getCounters(game).getCount("time");
                    AsForetoldAlternativeCost alternateCostAbility = new AsForetoldAlternativeCost(timeCounters);
                    alternateCostAbility.setSourceId(source.getSourceId());
                    controller.getAlternativeSourceCosts().add(alternateCostAbility);
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
