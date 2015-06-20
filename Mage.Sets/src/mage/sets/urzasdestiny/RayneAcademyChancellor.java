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
package mage.sets.urzasdestiny;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.EnchantedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class RayneAcademyChancellor extends CardImpl {

    public RayneAcademyChancellor(UUID ownerId) {
        super(ownerId, 43, "Rayne, Academy Chancellor", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "UDS";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card. You may draw an additional card if Rayne, Academy Chancellor is enchanted.
        this.addAbility(new RayneAcademyChancellorTriggeredAbility());
    }

    public RayneAcademyChancellor(final RayneAcademyChancellor card) {
        super(card);
    }

    @Override
    public RayneAcademyChancellor copy() {
        return new RayneAcademyChancellor(this);
    }
}

class RayneAcademyChancellorTriggeredAbility extends TriggeredAbilityImpl {
    
    RayneAcademyChancellorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(2), new DrawCardSourceControllerEffect(1), new EnchantedCondition(), "you may draw a card. You may draw an additional card if {this} is enchanted."), true);
    }
    
    RayneAcademyChancellorTriggeredAbility(final RayneAcademyChancellorTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public RayneAcademyChancellorTriggeredAbility copy() {
        return new RayneAcademyChancellorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGETED;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        Player targetter = game.getPlayer(event.getPlayerId());
        if (controller != null && targetter != null && !controller.getId().equals(targetter.getId())) {
            if (event.getTargetId().equals(controller.getId())) {
                return true;
            }
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null && this.getControllerId().equals(permanent.getControllerId())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, you may draw a card. You may draw an additional card if {this} is enchanted.";
    }
}
