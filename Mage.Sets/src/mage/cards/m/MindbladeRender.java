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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class MindbladeRender extends CardImpl {

    public MindbladeRender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.AZRA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever your opponents are dealt combat damage, if any of that damage was dealt by a Warrior, you draw a card and you lose 1 life.
        this.addAbility(new MindbladeRenderTriggeredAbility());
    }

    public MindbladeRender(final MindbladeRender card) {
        super(card);
    }

    @Override
    public MindbladeRender copy() {
        return new MindbladeRender(this);
    }
}

class MindbladeRenderTriggeredAbility extends TriggeredAbilityImpl {

    private boolean usedForCombatDamageStep;

    MindbladeRenderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.addEffect(new LoseLifeSourceControllerEffect(1));
        this.usedForCombatDamageStep = false;
    }

    MindbladeRenderTriggeredAbility(final MindbladeRenderTriggeredAbility effect) {
        super(effect);
        this.usedForCombatDamageStep = effect.usedForCombatDamageStep;
    }

    @Override
    public MindbladeRenderTriggeredAbility copy() {
        return new MindbladeRenderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent damager = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (damager == null) {
            return false;
        }
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                && event.getFlag()
                && controller.hasOpponent(event.getTargetId(), game)
                && damager.hasSubtype(SubType.WARRIOR, game)
                && !usedForCombatDamageStep) {
            usedForCombatDamageStep = true;
            return true;
        }
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST) {
            usedForCombatDamageStep = false;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever your opponents are dealt combat damage, if any of that damage was dealt by a Warrior, you draw a card and you lose 1 life.";
    }
}
