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

import java.util.UUID;
import mage.Constants;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Backfir3
 */
public class EchoAbility extends TriggeredAbilityImpl<EchoAbility> {

    protected UUID lastController;
    protected boolean echoPaid;
    protected String manaString;

    public EchoAbility(String manaString) {
        super(Constants.Zone.BATTLEFIELD, new EchoEffect(new ManaCostsImpl(manaString)), false);
        this.echoPaid = false;
        this.manaString = manaString;
    }

    public EchoAbility(final EchoAbility ability) {
        super(ability);
        this.echoPaid = ability.echoPaid;
        this.manaString = ability.manaString;
    }

    @Override
    public EchoAbility copy() {
        return new EchoAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if(zEvent.getToZone() != null && zEvent.getToZone() == Constants.Zone.BATTLEFIELD &&
                zEvent.getFromZone() == null && this.echoPaid) {
                this.echoPaid = false;
            }
        }
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            if(lastController != null){
                if(!lastController.equals(this.controllerId)){
                    this.echoPaid = false;
                }
            }
            lastController = this.getControllerId();
        }
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE &&
            event.getPlayerId().equals(this.controllerId) &&
            lastController.equals(this.controllerId) && !this.echoPaid){
            this.echoPaid = true;
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Echo " + manaString + " (At the beginning of your upkeep, if this came under your control since the beginning of your last upkeep," + getEffects().getText(modes.getMode()) + ")";
    }
}

class EchoEffect extends OneShotEffect<EchoEffect> {
    protected Cost cost;

    public EchoEffect(Cost costs) {
        super(Outcome.Sacrifice);
        this.cost = costs;
     }

    public EchoEffect(final EchoEffect effect) {
        super(effect);
        this.cost = effect.cost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) { 
            if (player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() /* + " or sacrifice " + permanent.getName() */ + "?", game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getId(), source.getControllerId(), false)) {
                    return true;
                                }
            }
            permanent.sacrifice(source.getSourceId(), game);
            return true;
        }
        return false;
    }

    @Override
    public EchoEffect copy() {
        return new EchoEffect(this);
    }

        @Override
    public String getText(Mode mode) {
            StringBuilder sb = new StringBuilder("sacrifice {this} unless you ");
            String costText = cost.getText();
            if (costText.toLowerCase().startsWith("discard")) {
                sb.append(costText.substring(0, 1).toLowerCase());
                sb.append(costText.substring(1));
            }
            else
                sb.append("pay ").append(costText);

            return sb.toString();

    }
}
