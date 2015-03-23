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
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.EvokedCondition;
import mage.abilities.costs.AlternativeCost2;
import mage.abilities.costs.AlternativeCost2Impl;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class EvokeAbility extends StaticAbility implements AlternativeSourceCosts {

    protected static final String EVOKE_KEYWORD = "Evoke";
    protected static final String REMINDER_TEXT = "(You may cast this spell for its evoke cost. If you do, it's sacrificed when it enters the battlefield.)";

    protected List<AlternativeCost2> evokeCosts = new LinkedList<>();

    // needed to check activation status, if card changes zone after casting it
    private   int zoneChangeCounter = 0;

    public EvokeAbility(Card card, String manaString) {
        super(Zone.ALL, null);
        name = EVOKE_KEYWORD;
        this.addEvokeCost(manaString);
        Ability ability = new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceEffect()), EvokedCondition.getInstance(), "Sacrifice {this} when it enters the battlefield and was evoked.");
        ability.setRuleVisible(false);
        addSubAbility(ability);

    }

    public EvokeAbility(final EvokeAbility ability) {
       super(ability);
       this.evokeCosts.addAll(ability.evokeCosts);
       this.zoneChangeCounter = ability.zoneChangeCounter;
    }

    @Override
    public EvokeAbility copy() {
       return new EvokeAbility(this);
    }
    
    public final AlternativeCost2 addEvokeCost(String manaString) {
       AlternativeCost2 evokeCost = new AlternativeCost2Impl(EVOKE_KEYWORD, REMINDER_TEXT, new ManaCostsImpl(manaString));
       evokeCosts.add(evokeCost);
       return evokeCost;
    }

    public void resetEvoke() {
        for (AlternativeCost2 cost: evokeCosts) {
            cost.reset();
        }
        zoneChangeCounter = 0;
    }

    @Override
    public boolean isActivated(Ability ability, Game game) {
        Card card = game.getCard(sourceId);
        if (card != null && card.getZoneChangeCounter() <= zoneChangeCounter +1) {
            for (AlternativeCost2 cost: evokeCosts) {
                if(cost.isActivated(game)) {
                    return true;
                }
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
            if (player != null) {
                this.resetEvoke();
                for (AlternativeCost2 evokeCost: evokeCosts) {
                    if (evokeCost.canPay(ability, sourceId, controllerId, game) &&
                        player.chooseUse(Outcome.Benefit, new StringBuilder(EVOKE_KEYWORD).append(" the creature for ").append(evokeCost.getText(true)).append(" ?").toString(), game)) {
                        activateEvoke(evokeCost, game);
                        ability.getManaCostsToPay().clear();
                        ability.getCosts().clear();
                        for (Iterator it = ((Costs) evokeCost).iterator(); it.hasNext();) {
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
        return isActivated(ability, game);
    }

    private void activateEvoke(AlternativeCost2 cost, Game game) {
        cost.activate();
        // remember zone change counter
        if (zoneChangeCounter == 0) {
            Card card = game.getCard(getSourceId());
            if (card != null) {
                zoneChangeCounter = card.getZoneChangeCounter();
            } else {
                throw new IllegalArgumentException("Evoke source card not found");
            }
        }
    }

    @Override
    public String getRule() {
       StringBuilder sb = new StringBuilder();
       int numberCosts = 0;
       String remarkText = "";
       for (AlternativeCost2 evokeCost: evokeCosts) {
           if (numberCosts == 0) {
               sb.append(evokeCost.getText(false));
               remarkText = evokeCost.getReminderText();
           } else {
               sb.append(" and/or ").append(evokeCost.getText(true));
           }
           ++numberCosts;
       }
       if (numberCosts == 1) {
            sb.append(" ").append(remarkText);
       }

       return sb.toString();
    }

    @Override
    public String getCastMessageSuffix(Game game) {
        StringBuilder sb = new StringBuilder();
        int position = 0;
        for (AlternativeCost2 cost : evokeCosts) {
            if (cost.isActivated(game)) {
                sb.append(cost.getCastSuffixMessage(position));
                ++position;
            }
        }
        return sb.toString();
    }
    
    @Override
    public Costs<Cost> getCosts() {
        Costs<Cost> alterCosts = new CostsImpl<>();
        for (AlternativeCost2 aCost: evokeCosts) {
            alterCosts.add(aCost.getCost());
        }
        return alterCosts;
    }    
}
