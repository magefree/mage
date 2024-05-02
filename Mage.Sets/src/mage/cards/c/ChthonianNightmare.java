package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourceControllerCountersCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.Filter;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ChthonianNightmare extends CardImpl {

    public ChthonianNightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // When Chthonian Nightmare enters the battlefield, you get {E}{E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(3)));

        // Pay X {E}, Sacrifice a creature, Return Chthonian Nightmare to its owner's hand: Return target creature card with mana value X from your graveyard to the battlefield. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
                        .setText("Return target creature card with mana value X from your graveyard to the battlefield"),
                new PayEnergyCost(0).setText("Pay X {E}") // Cost adjusted.
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        ability.addCost(new ReturnToHandFromBattlefieldSourceCost());
        ability.setTargetAdjuster(new ChthonianNightmareTargetAdjuster(SourceControllerCountersCount.ENERGY, ComparisonType.OR_LESS));
        ability.setCostAdjuster(ChthonianNightmareCostAdjuster.instance);
        this.addAbility(ability);
    }

    private ChthonianNightmare(final ChthonianNightmare card) {
        super(card);
    }

    @Override
    public ChthonianNightmare copy() {
        return new ChthonianNightmare(this);
    }
}

// TODO: replace with ManaValueTargetAdjuster in #12017
class ChthonianNightmareTargetAdjuster implements TargetAdjuster {
    private Target blueprintTarget = null;
    private final DynamicValue dynamicValue;
    private final ComparisonType comparison;

    ChthonianNightmareTargetAdjuster(DynamicValue value, ComparisonType compare) {
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

enum ChthonianNightmareCostAdjuster implements CostAdjuster {
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