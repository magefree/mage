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
package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 * @author Johnny E. Hastings
 */
public class SoulBurn extends CardImpl {

    static final FilterMana filterBlackOrRed = new FilterMana();

    static {
        filterBlackOrRed.setBlack(true);
        filterBlackOrRed.setRed(true);
    }

    public SoulBurn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{2}{B}");

        // Spend only black or red mana on X.
        // Soul Burn deals X damage to target creature or player. You gain life equal to the damage dealt for each black mana spent on X; not more life than the player's life total before Soul Burn dealt damage, or the creature's toughness.
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        this.getSpellAbility().addEffect(new SoulBurnEffect());
        VariableCost variableCost = this.getSpellAbility().getManaCostsToPay().getVariableCosts().get(0);
        if (variableCost instanceof VariableManaCost) {
            ((VariableManaCost) variableCost).setFilter(filterBlackOrRed);
        }
    }

    public SoulBurn(final SoulBurn card) {
        super(card);
    }

    @Override
    public SoulBurn copy() {
        return new SoulBurn(this);
    }
}

class SoulBurnEffect extends OneShotEffect {

    public SoulBurnEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to target creature or player for each black or red mana spent on X. You gain life equal to the damage dealt for each black mana spent; not more life than the player's life total before Soul Burn dealt damage, or the creature's toughness.";
    }

    public SoulBurnEffect(final SoulBurnEffect effect) {
        super(effect);
    }

    /***
     * @param game
     * @param source
     * @return 
     */
    @Override
    public boolean apply(Game game, Ability source) {
        
        // Get the colors we care about. (This isn't racist, honestly.)
        int amountBlack = source.getManaCostsToPay().getPayment().getBlack();
        int amountRed = source.getManaCostsToPay().getPayment().getRed();
        
        // Get the colors we don't really care about. (See note above.)
        int amountWhite = source.getManaCostsToPay().getPayment().getWhite();
        int amountGreen = source.getManaCostsToPay().getPayment().getGreen();
        int amountBlue = source.getManaCostsToPay().getPayment().getBlue();
        int amountColorless = source.getManaCostsToPay().getPayment().getColorless();
        
        // Figure out what was spent on the spell in total, determine proper values for 
        // black and red, minus initial casting cost. 
        int totalColorlessForCastingCost = amountWhite + amountGreen + amountBlue + amountColorless;
        int amountOffsetByColorless = 0;
        if (totalColorlessForCastingCost > 0) {
            amountOffsetByColorless = totalColorlessForCastingCost;
            if (amountOffsetByColorless > 2) {
                // The game should never let this happen, but I'll check anyway since I don't know
                // the guts of the game [yet]. 
                amountOffsetByColorless = 2;
            }                
        }
            
        // Remove 1 black to account for casting cost. 
        amountBlack--;
        
        // Determine if we need to offset the red or black values any further due to the 
        // amount of non-red and non-black paid. 
        if (amountOffsetByColorless < 2) {
            int amountToOffsetBy = 2 - amountOffsetByColorless;
            
            if (amountRed > 0) {
                if (amountRed >= amountToOffsetBy) {
                    // Pay all additional unpaid casting cost with red.
                    amountRed = amountRed - amountToOffsetBy;
                } else {
                    // Red paid doesn't cover the 2 default required by the spell.
                    // Pay some in red, and some in black. 
                    // If we're here, red is 1, and amountToOffetBy is 2. 
                    // That means we can subtract 1 from both red and black.
                    amountRed--;
                    amountBlack--;
                }
            } else {
                // Pay all additional unpaid casting cost with black.
                amountBlack = amountBlack - amountToOffsetBy;
            }
        }
        
        int totalXAmount = amountBlack + amountRed;
                
        int lifetogain = amountBlack;
        if (totalXAmount > 0) {
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null ) {
                if (permanent.getToughness().getValue() < lifetogain) {
                    lifetogain = permanent.getToughness().getValue();
                }
                permanent.damage(totalXAmount, source.getSourceId(), game, false, true);
            } else {
                Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
                if (player != null) {
                    if (player.getLife() < lifetogain) {
                        lifetogain = player.getLife();
                    }
                    player.damage(totalXAmount, source.getSourceId(), game, false, true);
                } else {
                    return false;
                }
            }
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.gainLife(lifetogain, game);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public SoulBurnEffect copy() {
        return new SoulBurnEffect(this);
    }

}
