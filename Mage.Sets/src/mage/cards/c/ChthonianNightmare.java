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
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

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
                new PayEnergyCost(0).setText("Pay X {E}") // TODO: replace with proper VariableEnergyCost
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        ability.addCost(new ReturnToHandFromBattlefieldSourceCost());
        ability.setTargetAdjuster(new XManaValueTargetAdjuster());
        ability.setCostAdjuster(ChthonianNightmareCostAdjuster.instance); // TODO: remove
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
