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
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

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
        ability.setTargetAdjuster(new XManaValueTargetAdjuster());
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
