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
package mage.abilities.keyword;

import java.util.Iterator;
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
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 * 702.25. Buyback
 *
 *   702.25a Buyback appears on some instants and sorceries. It represents two static 
 *   abilities that function while the spell is on the stack. "Buyback [cost]" means 
 *   "You may pay an additional [cost] as you cast this spell" and "If the buyback 
 *   cost was paid, put this spell into its owner's hand instead of into that player's
 *   graveyard as it resolves." Paying a spell's buyback cost follows the rules for 
 *   paying additional costs in rules 601.2b and 601.2e-g.
 *
 * @author LevelX2
 */

public class BuybackAbility extends StaticAbility<BuybackAbility> implements OptionalAdditionalSourceCosts {

    private static final String keywordText = "Buyback";
    private static final String reminderTextCost = "<i>(You may {cost} in addition to any other costs as you cast this spell. If you do, put this card into your hand as it resolves.)</i>";
    private static final String reminderTextMana = "<i>(You may pay an additional {cost} as you cast this spell. If you do, put this card into your hand as it resolves.)</i>";
    protected OptionalAdditionalCost buybackCost;

    public BuybackAbility(String manaString) {
       super(Zone.STACK, new BuybackEffect());
       this.buybackCost = new OptionalAdditionalCostImpl(keywordText, reminderTextMana, new ManaCostsImpl(manaString));
       setRuleAtTheTop(true);
    }
    
    public BuybackAbility(Cost cost) {
       super(Zone.STACK, new BuybackEffect());
       this.buybackCost = new OptionalAdditionalCostImpl(keywordText, "-", reminderTextCost, cost);
       setRuleAtTheTop(true);
    }

    public BuybackAbility(final BuybackAbility ability) {
       super(ability);
       buybackCost = ability.buybackCost;
    }

    @Override
    public BuybackAbility copy() {
       return new BuybackAbility(this);
    }

    @Override
    public void addCost(Cost cost) {
        if (buybackCost != null) {
            ((Costs) buybackCost).add(cost);
        }
    }

    public boolean isActivated() {
        if (buybackCost != null) {
            return buybackCost.isActivated();
        }
        return false;
    }

    public void resetBuyback() {
        if (buybackCost != null) {
            buybackCost.reset();
        }
    }

    @Override
    public void addOptionalAdditionalCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player player = game.getPlayer(controllerId);
            if (player != null) {
                this.resetBuyback();
                if (buybackCost != null) {
                    if (player.chooseUse(Constants.Outcome.Benefit,new StringBuilder("Pay ").append(buybackCost.getText(false)).append(" ?").toString(), game)) {
                        buybackCost.activate();
                        for (Iterator it = ((Costs) buybackCost).iterator(); it.hasNext();) {
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
    }


    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        if (buybackCost != null) {
            sb.append(buybackCost.getText(false));
            sb.append(" ").append(buybackCost.getReminderText());
        }
        return sb.toString();
    }

    @Override
    public String getCastMessageSuffix() {
        if (buybackCost != null) {
            return buybackCost.getCastSuffixMessage(0);
        } else {
            return "";
        }
    }

    public String getReminderText() {
        if (buybackCost != null) {
            return buybackCost.getReminderText();
        } else {
            return "";
        }
    }
}

class BuybackEffect extends ReplacementEffectImpl<BuybackEffect> {

    public BuybackEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Exile);
        staticText = "When {this} resolves and you payed buyback costs, put it back to hand instead";
    }

    public BuybackEffect(final BuybackEffect effect) {
        super(effect);
    }

    @Override
    public BuybackEffect copy() {
        return new BuybackEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Zone.STACK && 
                    (event.getAppliedEffects() == null || !event.getAppliedEffects().contains(this.getId()))) {
                event.getAppliedEffects().add(this.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(source.getSourceId());
        if (card != null && source instanceof BuybackAbility) {
            if (((BuybackAbility) source).isActivated()) {
                return card.moveToZone(Zone.HAND, source.getId(), game, true, event.getAppliedEffects());
            }
        }
        return false;
    }

}