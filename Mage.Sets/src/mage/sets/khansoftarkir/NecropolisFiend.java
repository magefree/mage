/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.khansoftarkir;

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
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
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
public class NecropolisFiend extends CardImpl {

    public NecropolisFiend(UUID ownerId) {
        super(ownerId, 82, "Necropolis Fiend", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{7}{B}{B}");
        this.expansionSetCode = "KTK";
        this.subtype.add("Demon");

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Delve
        this.addAbility(new DelveAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
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
    public void adjustChoices(Ability ability, Game game) {
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
    public void adjustCosts(Ability ability, Game game) {
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
