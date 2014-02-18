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
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.AlternativeCost2;
import mage.abilities.costs.AlternativeCost2Impl;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.ProwlWatcher;

/**
 * 702.74. Prowl #
 * 
 * 702.74a Prowl is a static ability that functions on the stack. "Prowl [cost]" means 
 * "You may pay [cost] rather than pay this spell's mana cost if a player was dealt combat
 * damage this turn by a source that, at the time it dealt that damage, was under your
 * control and had any of this spell's creature types." Paying a spell's prowl cost follows
 * the rules for paying alternative costs in rules 601.2b and 601.2e-g
 *
 * @author LevelX2
 */

public class ProwlAbility extends StaticAbility<ProwlAbility> implements AlternativeSourceCosts {

    private static final String PROWL_KEYWORD = "Prowl";
    private final List<AlternativeCost2> prowlCosts = new LinkedList<AlternativeCost2>();
    private String reminderText;

    public ProwlAbility(Card card, String manaString) {
        super(Zone.STACK, null);
        setRuleAtTheTop(true);
        name = PROWL_KEYWORD;
        setReminderText(card);
        this.addProwlCost(manaString);
        card.addWatcher(new ProwlWatcher());

    }

    public ProwlAbility(final ProwlAbility ability) {
       super(ability);
       this.prowlCosts.addAll(ability.prowlCosts);
       this.reminderText = ability.reminderText;
    }

    @Override
    public ProwlAbility copy() {
       return new ProwlAbility(this);
    }

    public final AlternativeCost2 addProwlCost(String manaString) {
       AlternativeCost2 prowlCost = new AlternativeCost2Impl(PROWL_KEYWORD, reminderText, new ManaCostsImpl(manaString));
       prowlCosts.add(prowlCost);
       return prowlCost;
    }

    public void resetProwl() {
        for (AlternativeCost2 cost: prowlCosts) {
            cost.reset();
        }
    }
    @Override
    public boolean isActivated() {
        for (AlternativeCost2 cost: prowlCosts) {
            if(cost.isActivated()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isAvailable(Ability source, Game game) {
        return true;
    }
    
    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(controllerId);
            ProwlWatcher prowlWatcher = (ProwlWatcher) game.getState().getWatchers().get("Prowl");
            Card card = game.getCard(ability.getSourceId());
            if (player == null || prowlWatcher == null || card == null) {
                throw new IllegalArgumentException("Params can't be null");
            }
            boolean canProwl = false;
            for (String subtype: card.getSubtype()) {
                if (prowlWatcher.hasSubtypeMadeCombatDamage(ability.getControllerId(), subtype)) {
                    canProwl = true;
                    break;
                }
            }
            if (canProwl) {
                this.resetProwl();
                for (AlternativeCost2 prowlCost: prowlCosts) {
                    if (prowlCost.canPay(sourceId, controllerId, game) &&
                          player.chooseUse(Outcome.Benefit, new StringBuilder("Cast for ").append(PROWL_KEYWORD).append(" cost ").append(prowlCost.getText(true)).append(" ?").toString(), game)) {
                        prowlCost.activate();
                        ability.getManaCostsToPay().clear();
                        ability.getCosts().clear();
                        for (Iterator it = ((Costs) prowlCost).iterator(); it.hasNext();) {
                            Cost cost = (Cost) it.next();
                            if (cost instanceof ManaCostsImpl) {
                                ability.getManaCostsToPay().add((ManaCostsImpl) cost.copy());
                            } else {
                                ability.getCosts().add(cost.copy());
                            }
                        }
                    }
                }
            }
        }
        return isActivated();
    }

    @Override
    public String getRule() {
       StringBuilder sb = new StringBuilder();
       int numberCosts = 0;
       String remarkText = "";
       for (AlternativeCost2 prowlCost: prowlCosts) {
           if (numberCosts == 0) {
               sb.append(prowlCost.getText(false));
               remarkText = prowlCost.getReminderText();
           } else {
               sb.append(" and/or ").append(prowlCost.getText(true));
           }
           ++numberCosts;
       }
       if (numberCosts == 1) {
            sb.append(" ").append(remarkText);
       }

       return sb.toString();
    }

    @Override
    public String getCastMessageSuffix() {
        StringBuilder sb = new StringBuilder();
        int position = 0;
        for (AlternativeCost2 cost : prowlCosts) {
            if (cost.isActivated()) {
                sb.append(cost.getCastSuffixMessage(position));
                ++position;
            }
        }
        return sb.toString();
    }

    private void setReminderText(Card card) {
        StringBuilder sb = new StringBuilder("(You may cast this for its prowl cost if you dealt combat damage to a player this turn with a ");
        int i = 0;
        for (String subtype: card.getSubtype()) {
            i++;
            sb.append(subtype);
            if (card.getSupertype().size() > 1 && i < card.getSupertype().size()) {
                sb.append(" or ");
            }
        }
    //private static final String REMINDER_TEXT = "{subtypes}.)";

        reminderText = sb.toString();
    }
}
