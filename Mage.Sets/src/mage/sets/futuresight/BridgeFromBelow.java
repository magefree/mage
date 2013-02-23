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
package mage.sets.futuresight;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class BridgeFromBelow extends CardImpl<BridgeFromBelow> {

    
    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Whenever a nontoken creature is put into your graveyard from the battlefield");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("When a creature is put into an opponent's graveyard from the battlefield");
    
    static{
        filter1.add(new ControllerPredicate(Constants.TargetController.YOU));
        filter1.add(Predicates.not(new TokenPredicate()));
        filter2.add(new ControllerPredicate(Constants.TargetController.OPPONENT));
    }
    
    public BridgeFromBelow(UUID ownerId) {
        super(ownerId, 81, "Bridge from Below", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");
        this.expansionSetCode = "FUT";

        this.color.setBlack(true);

        // Whenever a nontoken creature is put into your graveyard from the battlefield, if Bridge from Below is in your graveyard, put a 2/2 black Zombie creature token onto the battlefield.
        this.addAbility(new BridgeFromBelowAbility(new CreateTokenEffect(new ZombieToken()), filter1));
        // When a creature is put into an opponent's graveyard from the battlefield, if Bridge from Below is in your graveyard, exile Bridge from Below.
        this.addAbility(new BridgeFromBelowAbility(new ExileSourceEffect(), filter2));
    }

    public BridgeFromBelow(final BridgeFromBelow card) {
        super(card);
    }

    @Override
    public BridgeFromBelow copy() {
        return new BridgeFromBelow(this);
    }
}

class BridgeFromBelowAbility extends TriggeredAbilityImpl<BridgeFromBelowAbility> {

    protected FilterCreaturePermanent filter;

    public BridgeFromBelowAbility(Effect effect, FilterCreaturePermanent filter) {
        super(Constants.Zone.GRAVEYARD, effect, false);
        this.filter = filter;
    }

    public BridgeFromBelowAbility(BridgeFromBelowAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public BridgeFromBelowAbility copy() {
        return new BridgeFromBelowAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;


            if (zEvent.getFromZone() == Constants.Zone.BATTLEFIELD && zEvent.getToZone() == Constants.Zone.GRAVEYARD) {
                Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
                if (permanent != null && filter.match(permanent, sourceId, controllerId, game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        if(controller != null && controller.getGraveyard().contains(this.getSourceId()))        {
            return true;
        }
        return false;
    }
    
    

    @Override
    public String getRule() {
        return filter.getMessage() +", if Bridge from Below is in your graveyard, " + super.getRule();
    }
}
