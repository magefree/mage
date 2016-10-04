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
package mage.sets.judgment;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author emerald000
 */
public class CephalidConstable extends CardImpl {

    public CephalidConstable(UUID ownerId) {
        super(ownerId, 35, "Cephalid Constable", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "JUD";
        this.subtype.add("Cephalid");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Cephalid Constable deals combat damage to a player, return up to that many target permanents that player controls to their owners' hands.
        Ability ability = new CephalidConstableTriggeredAbility();
        ability.addTarget(new TargetPermanent(0, 1, new FilterPermanent(), false)); // Ajusted when it triggers.
        this.addAbility(ability);
    }

    public CephalidConstable(final CephalidConstable card) {
        super(card);
    }

    @Override
    public CephalidConstable copy() {
        return new CephalidConstable(this);
    }
}

class CephalidConstableTriggeredAbility extends TriggeredAbilityImpl {

    CephalidConstableTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), false);
    }

    CephalidConstableTriggeredAbility(final CephalidConstableTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CephalidConstableTriggeredAbility copy() {
        return new CephalidConstableTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId) && ((DamagedPlayerEvent) event).isCombatDamage()) {
            FilterPermanent filter = new FilterPermanent("permanent" + (event.getAmount() > 1 ? "s" : "") + " damaged player control");
            filter.add(new ControllerIdPredicate(event.getPlayerId()));
            Target target = new TargetPermanent(0, event.getAmount(), filter, false);
            this.getTargets().clear();
            this.getTargets().add(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, return up to that many target permanents that player controls to their owners' hands";
    }

}
