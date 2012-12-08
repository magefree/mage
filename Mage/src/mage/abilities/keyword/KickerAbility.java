/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.keyword;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import mage.Constants;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.OptionalAdditionalCost;
import mage.abilities.costs.OptionalAdditionalSourceCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.game.Game;
import mage.players.Player;

public class KickerAbility extends StaticAbility<KickerAbility> implements OptionalAdditionalSourceCosts {

    protected List<OptionalAdditionalCost> kickerCosts = new LinkedList<OptionalAdditionalCost>();

    public KickerAbility(OptionalAdditionalCost kickerCost) {
       super(Zone.STACK, null);
       kickerCosts.add(kickerCost);
       setRuleAtTheTop(true);
    }

    public KickerAbility(final KickerAbility ability) {
       super(ability);
       this.kickerCosts = ability.kickerCosts;
    }

    @Override
    public KickerAbility copy() {
       return new KickerAbility(this);
    }

    public void resetKicker() {
        for (OptionalAdditionalCost cost: kickerCosts) {
            cost.reset();
        }
    }

    public List<OptionalAdditionalCost> getKickerCosts () {
        return kickerCosts;
    }

    public void addKickerManaCost(OptionalAdditionalCost kickerCost) {
        kickerCosts.add(kickerCost);
    }
    
    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(controllerId);
            if (player != null) {
                this.resetKicker();
                for (OptionalAdditionalCost kickerCost: kickerCosts) {
                    boolean again = true;
                    while (again) {
                        String times = "";
                        if (kickerCost.isRepeatable()) {
                            int activated = kickerCost.getActivateCount();
                            times = Integer.toString(activated + 1) + (activated == 0 ? " time ":" times ");
                        }
                        if (player.chooseUse(Constants.Outcome.Benefit, "Pay " + times + kickerCost.getText(false) + " ?", game)) {
                            kickerCost.activate();
                            for (Iterator it = ((Costs) kickerCost).iterator(); it.hasNext();) {
                                Cost cost = (Cost) it.next();
                                if (cost instanceof ManaCostsImpl) {
                                    ability.getManaCostsToPay().add((ManaCostsImpl) cost.copy());
                                } else {
                                    ability.getCosts().add(cost.copy());
                                }
                            }
                            
                            again = kickerCost.isRepeatable();
                        } else {
                            again = false;
                        }
                    }
                }
            }
        }
    }


    @Override
    public String getRule() {
       StringBuilder sb = new StringBuilder();
       int numberKicker = 0;
       String remarkText = "";
       for (OptionalAdditionalCost kickerCost: kickerCosts) {
           if (numberKicker == 0) {
               sb.append(kickerCost.getText(false));
               remarkText = kickerCost.getReminderText();
           } else {
               sb.append(" and/or ").append(kickerCost.getText(true));
           }
           ++numberKicker;
       }
       if (numberKicker == 1) {
            sb.append(" ").append(remarkText);
       }

       return sb.toString();
    }

    @Override
    public String getCastMessageSuffix() {
        StringBuilder sb = new StringBuilder();
        int position = 0;
        for (OptionalAdditionalCost cost : kickerCosts) {
            if (cost.isActivated()) {
                sb.append(cost.getCastSuffixMessage(position));
                ++position;
            }
        }
        return sb.toString();
    }
}
