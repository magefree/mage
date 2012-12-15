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
import mage.abilities.costs.OptionalAdditionalCostImpl;
import mage.abilities.costs.OptionalAdditionalSourceCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * 20121001 702.31. Kicker
 *   702.31a Kicker is a static ability that functions while the spell with kicker
 *           is on the stack. "Kicker [cost]" means "You may pay an additional [cost]
 *           as you cast this spell." Paying a spell's kicker cost(s) follows the
 *           rules for paying additional costs in rules 601.2b and 601.2e-g.
 *   702.31b The phrase "Kicker [cost 1] and/or [cost 2]" means the same thing as
 *           "Kicker [cost 1], kicker [cost 2]."
 *   702.31c Multikicker is a variant of the kicker ability. "Multikicker [cost]"
 *           means "You may pay an additional [cost] any number of times as you cast
 *           this spell." A multikicker cost is a kicker cost.
 *   702.31d If a spell's controller declares the intention to pay any of that spell's
 *           kicker costs, that spell has been "kicked." If a spell has two kicker
 *           costs or has multikicker, it may be kicked multiple times. See rule 601.2b.
 *   702.31e Objects with kicker or multikicker have additional abilities that specify
 *           what happens if they are kicked. These abilities are linked to the kicker
 *           or multikicker abilities printed on that object: they can refer only to
 *           those specific kicker or multikicker abilities. See rule 607,
 *           "Linked Abilities."
 *   702.31f Objects with more than one kicker cost have abilities that each correspond
 *           to a specific kicker cost. They contain the phrases "if it was kicked with
 *           its [A] kicker" and "if it was kicked with its [B] kicker," where A and B
 *           are the first and second kicker costs listed on the card, respectively. Each
 *           of those abilities is linked to the appropriate kicker ability.
 *   702.31g If part of a spell's ability has its effect only if that spell was kicked,
 *           and that part of the ability includes any targets, the spell's controller
 *           chooses those targets only if that spell was kicked. Otherwise, the spell is
 *           cast as if it did not have those targets. See rule 601.2c.
 *
 * @author LevelX2
 *
 */
public class KickerAbility extends StaticAbility<KickerAbility> implements OptionalAdditionalSourceCosts {

    protected static final String KickerKeyword = "Kicker";
    protected static final String KickerReminderMana = "(You may pay an additional {cost} as you cast this spell.)";
    protected static final String KickerReminderCost = "(You may {cost} in addition to any other costs as you cast this spell.)";

    protected String keywordText;
    protected String reminderText;
    protected List<OptionalAdditionalCost> kickerCosts = new LinkedList<OptionalAdditionalCost>();

    public KickerAbility(String manaString) {
       this(KickerKeyword, KickerReminderMana);
       this.addKickerCost(manaString);
    }

    public KickerAbility(Cost cost) {
       this(KickerKeyword, KickerReminderCost);
       this.addKickerCost(cost);
    }

    public KickerAbility(String keywordText, String reminderText) {
       super(Zone.STACK, null);
       name = keywordText;
       this.keywordText = keywordText;
       this.reminderText = reminderText;
       setRuleAtTheTop(true);
    }

    public KickerAbility(final KickerAbility ability) {
       super(ability);
       this.kickerCosts = ability.kickerCosts;
       this.keywordText = ability.keywordText;
       this.reminderText = ability.reminderText;
    }

    @Override
    public KickerAbility copy() {
       return new KickerAbility(this);
    }

    public final OptionalAdditionalCost addKickerCost(String manaString) {
       OptionalAdditionalCost kickerCost = new OptionalAdditionalCostImpl(keywordText, reminderText, new ManaCostsImpl(manaString));
       kickerCosts.add(kickerCost);
       return kickerCost;
    }

    public final OptionalAdditionalCost addKickerCost(Cost cost) {
       OptionalAdditionalCost kickerCost = new OptionalAdditionalCostImpl(keywordText, "-", reminderText, cost);
       kickerCosts.add(kickerCost);
       return kickerCost;
    }

    public void resetKicker() {
        for (OptionalAdditionalCost cost: kickerCosts) {
            cost.reset();
        }
    }

    public int getKickedCounter() {
        int counter = 0;
        for (OptionalAdditionalCost cost: kickerCosts) {
            counter += cost.getActivateCount();
        }
        return counter;
    }

    public boolean isKicked() {
        for (OptionalAdditionalCost cost: kickerCosts) {
            if(cost.isActivated()) {
                return true;
            }
        }
        return false;
    }

    public List<OptionalAdditionalCost> getKickerCosts () {
        return kickerCosts;
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
                        if (kickerCost.canPay(sourceId, controllerId, game) &&
                                player.chooseUse(Constants.Outcome.Benefit, new StringBuilder("Pay ").append(times).append(kickerCost.getText(false)).append(" ?").toString(), game)) {
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
