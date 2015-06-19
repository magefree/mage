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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddManaToManaPoolTargetControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class SakikoMotherOfSummer extends CardImpl {

    public SakikoMotherOfSummer(UUID ownerId) {
        super(ownerId, 141, "Sakiko, Mother of Summer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Snake");
        this.subtype.add("Shaman");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature you control deals combat damage to a player, add that much {G} to your mana pool. Until end of turn, this mana doesn't empty from your mana pool as steps and phases end.
        this.addAbility(new SakikoMotherOfSummerTriggeredAbility());
        
    }

    public SakikoMotherOfSummer(final SakikoMotherOfSummer card) {
        super(card);
    }

    @Override
    public SakikoMotherOfSummer copy() {
        return new SakikoMotherOfSummer(this);
    }
}

class SakikoMotherOfSummerTriggeredAbility extends TriggeredAbilityImpl {

    public SakikoMotherOfSummerTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public SakikoMotherOfSummerTriggeredAbility(final SakikoMotherOfSummerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SakikoMotherOfSummerTriggeredAbility copy() {
        return new SakikoMotherOfSummerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.getControllerId().equals(controllerId)) {
                this.getEffects().clear();
                Effect effect = new AddManaToManaPoolTargetControllerEffect(new Mana(0,event.getAmount(),0,0,0,0,0), "that player", true);
                effect.setTargetPointer(new FixedTarget(creature.getControllerId()));
                effect.setText("add that much {G} to your mana pool. Until end of turn, this mana doesn't empty from your mana pool as steps and phases end");        
                this.addEffect(effect);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control deals combat damage to a player, add that much {G} to your mana pool. Until end of turn, this mana doesn't empty from your mana pool as steps and phases end.";
    }
}