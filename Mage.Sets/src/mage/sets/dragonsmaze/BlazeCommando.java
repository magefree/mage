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

package mage.sets.dragonsmaze;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierTokenWithHaste;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */


public class BlazeCommando extends CardImpl {

    public BlazeCommando (UUID ownerId) {
        super(ownerId, 56, "Blaze Commando", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Minotaur");
        this.subtype.add("Soldier");


        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Whenever an instant or sorcery spell you control deals damage, put two 1/1 red and white Soldier creature tokens with haste onto the battlefield.
        this.addAbility(new BlazeCommandoTriggeredAbility());

    }

    public BlazeCommando (final BlazeCommando card) {
        super(card);
    }

    @Override
    public BlazeCommando copy() {
        return new BlazeCommando(this);
    }

}

class BlazeCommandoTriggeredAbility extends TriggeredAbilityImpl {

    private final List<UUID> handledStackObjects = new ArrayList<>();

    public BlazeCommandoTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SoldierTokenWithHaste(), 2), false);
    }

    public BlazeCommandoTriggeredAbility(final BlazeCommandoTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BlazeCommandoTriggeredAbility copy() {
        return new BlazeCommandoTriggeredAbility(this);
    }

    @Override
    public void reset(Game game) {
        /**
         * Blaze Commando's ability triggers each time an instant or sorcery spell you control
         * deals damage (or, put another way, the number of times the word “deals” appears in
         * its instructions), no matter how much damage is dealt or how many players or permanents
         * are dealt damage. For example, if you cast Punish the Enemy and it “deals 3 damage to
         * target player and 3 damage to target creature,” Blaze Commando's ability will trigger
         * once and you'll get two Soldier tokens.
         */
        handledStackObjects.clear();
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE || event.getType() == EventType.DAMAGED_PLANESWALKER || event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (getControllerId().equals(game.getControllerId(event.getSourceId()))) {
            MageObject damageSource = game.getObject(event.getSourceId());
            if (damageSource != null) {
                if (damageSource.getCardType().contains(CardType.INSTANT) || damageSource.getCardType().contains(CardType.SORCERY)) {
                    if (!handledStackObjects.contains(damageSource.getId())) {
                        handledStackObjects.add(damageSource.getId());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever an instant or sorcery spell you control deals damage, ").append(super.getRule()).toString();
    }
}
