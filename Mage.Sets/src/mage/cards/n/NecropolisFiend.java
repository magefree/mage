
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
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
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class NecropolisFiend extends CardImpl {

    public NecropolisFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Delve
        this.addAbility(new DelveAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        //TODO: Make ability properly copiable
        // {X}, {T}, Exile X cards from your graveyard: Target creature gets -X/-X until end of turn.
        DynamicValue xValue = new SignInversionDynamicValue(new ManacostVariableValue());
        Effect effect = new BoostTargetEffect(xValue,xValue,Duration.EndOfTurn);
        effect.setText("Target creature gets -X/-X until end of turn");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(1,1,new FilterCard("cards from your graveyard")), "Exile X cards from your graveyard"));
        this.addAbility(ability);

    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof SimpleActivatedAbility) {
            Player controller = game.getPlayer(ability.getControllerId());
            if (controller != null) {
                for (VariableCost variableCost: ability.getManaCostsToPay().getVariableCosts()) {
                    if (variableCost instanceof VariableManaCost) {
                        ((VariableManaCost)variableCost).setMaxX(controller.getGraveyard().size());
                    }
                }
            }
        }
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SimpleActivatedAbility) {
            int xValue = ability.getManaCostsToPay().getX();
            for(Cost cost: ability.getCosts()) {
                if (cost instanceof ExileFromGraveCost) {
                    ExileFromGraveCost exileCost = (ExileFromGraveCost) cost;
                    for(Target target: exileCost.getTargets()) {
                        if (target instanceof TargetCardInYourGraveyard) {
                            target.setMaxNumberOfTargets(xValue);
                            target.setMinNumberOfTargets(xValue);
                        }
                    }

                }
            }
        }
    }

    public NecropolisFiend(final NecropolisFiend card) {
        super(card);
    }

    @Override
    public NecropolisFiend copy() {
        return new NecropolisFiend(this);
    }
}
