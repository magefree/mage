package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourceControllerCountersCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.Filter;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HELIOSOne extends CardImpl {

    public HELIOSOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: You get {E}.
        Ability ability = new SimpleActivatedAbility(
                new GetEnergyCountersControllerEffect(1),
                new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {3}, {T}, Pay X {E}, Sacrifice HELIOS One: Destroy target nonland permanent with mana value X. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new DestroyTargetEffect().setText("Destroy target nonland permanent with mana value X"),
                new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayEnergyCost(0).setText("Pay X {E}")); // Cost adjusted.
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetNonlandPermanent());
        ability.setTargetAdjuster(new HELIOSOneTargetAdjuster(SourceControllerCountersCount.ENERGY, ComparisonType.OR_LESS));
        ability.setCostAdjuster(HELIOSOneCostAdjuster.instance);
        this.addAbility(ability);
    }

    private HELIOSOne(final HELIOSOne card) {
        super(card);
    }

    @Override
    public HELIOSOne copy() {
        return new HELIOSOne(this);
    }
}

// TODO: replace with ManaValueTargetAdjuster in #12017
class HELIOSOneTargetAdjuster implements TargetAdjuster {
    private Target blueprintTarget = null;
    private final DynamicValue dynamicValue;
    private final ComparisonType comparison;

    HELIOSOneTargetAdjuster(DynamicValue value, ComparisonType compare) {
        this.dynamicValue = value;
        this.comparison = compare;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (blueprintTarget == null) {
            blueprintTarget = ability.getTargets().get(0).copy();
            blueprintTarget.clearChosen();
        }
        Target newTarget = blueprintTarget.copy();
        int amount = dynamicValue.calculate(game, ability, ability.getEffects().get(0));
        Filter<MageObject> filter = newTarget.getFilter();
        filter.add(new ManaValuePredicate(comparison, amount));
        newTarget.setTargetName(filter.getMessage() + " (Mana Value " + comparison + " " + amount + ")");
        ability.getTargets().clear();
        ability.addTarget(newTarget);
    }
}

enum HELIOSOneCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        MageObject target = game.getObject(ability.getFirstTarget());
        if (target == null) {
            return;
        }
        int mv = target.getManaValue();
        Costs<Cost> costs = new CostsImpl<>();
        costs.addAll(ability.getCosts());
        ability.clearCosts();
        for (Cost cost : costs) {
            if (cost instanceof PayEnergyCost) {
                if (mv > 0) {
                    ability.addCost(new PayEnergyCost(mv));
                }
            } else {
                ability.addCost(cost);
            }
        }
    }
}