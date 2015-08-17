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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LoneFox

 */
public class PutridWarrior extends CardImpl {

    public PutridWarrior(UUID ownerId) {
        super(ownerId, 117, "Putrid Warrior", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}{B}");
        this.expansionSetCode = "APC";
        this.subtype.add("Zombie");
        this.subtype.add("Soldier");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Putrid Warrior deals damage, choose one - Each player loses 1 life; or each player gains 1 life.
        Ability ability = new PutridWarriorDealsDamageTriggeredAbility(new LoseLifeAllPlayersEffect(1));
        Mode mode = new Mode();
        mode.getEffects().add(new PutridWarriorGainLifeEffect());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    public PutridWarrior(final PutridWarrior card) {
        super(card);
    }

    @Override
    public PutridWarrior copy() {
        return new PutridWarrior(this);
    }
}


class PutridWarriorDealsDamageTriggeredAbility extends TriggeredAbilityImpl {

    public PutridWarriorDealsDamageTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public PutridWarriorDealsDamageTriggeredAbility(final PutridWarriorDealsDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PutridWarriorDealsDamageTriggeredAbility copy() {
        return new PutridWarriorDealsDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER || event.getType() == EventType.DAMAGED_CREATURE
            || event.getType() == EventType.DAMAGED_PLANESWALKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.sourceId);
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage, " + super.getRule();
    }
}


class PutridWarriorGainLifeEffect extends OneShotEffect {

    public PutridWarriorGainLifeEffect() {
        super(Outcome.GainLife);
        staticText = "Each player gains 1 life.";
    }

    public PutridWarriorGainLifeEffect(final PutridWarriorGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public PutridWarriorGainLifeEffect copy() {
        return new PutridWarriorGainLifeEffect(this);
    }

    @Override
        public boolean apply(Game game, Ability source) {
        for(UUID playerId: game.getPlayer(source.getControllerId()).getInRange()) {
            Player player = game.getPlayer(playerId);
            if(player != null) {
                player.gainLife(1, game);
            }
        }
        return true;
    }

}
