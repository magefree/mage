
package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DelveAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NecropolisFiend extends CardImpl {

    public NecropolisFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Delve
        this.addAbility(new DelveAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {X}, {T}, Exile X cards from your graveyard: Target creature gets -X/-X until end of turn.
        DynamicValue xValue = new SignInversionDynamicValue(ManacostVariableValue.REGULAR);
        Effect effect = new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn);
        effect.setText("Target creature gets -X/-X until end of turn");
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD, effect,
                new ManaCostsImpl("{X}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(
                1, 1, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD
        ), "Exile X cards from your graveyard"));
        ability.setTargetAdjuster(NecropolisFiendTargetAdjuster.instance);
        ability.setCostAdjuster(NecropolisFiendCostAdjuster.instance);
        this.addAbility(ability);

    }

    private NecropolisFiend(final NecropolisFiend card) {
        super(card);
    }

    @Override
    public NecropolisFiend copy() {
        return new NecropolisFiend(this);
    }
}

enum NecropolisFiendCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return;
        }
        for (VariableCost variableCost : ability.getManaCostsToPay().getVariableCosts()) {
            if (variableCost instanceof VariableManaCost) {
                ((VariableManaCost) variableCost).setMaxX(controller.getGraveyard().size());
            }
        }
    }
}

enum NecropolisFiendTargetAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        for (Cost cost : ability.getCosts()) {
            if (!(cost instanceof ExileFromGraveCost)) {
                continue;
            }
            ExileFromGraveCost exileCost = (ExileFromGraveCost) cost;
            for (Target target : exileCost.getTargets()) {
                if (target instanceof TargetCardInYourGraveyard) {
                    target.setMaxNumberOfTargets(xValue);
                    target.setMinNumberOfTargets(xValue);
                }
            }
        }
    }
}
