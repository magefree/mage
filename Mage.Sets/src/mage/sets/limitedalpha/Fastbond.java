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
package mage.sets.limitedalpha;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continious.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class Fastbond extends CardImpl<Fastbond> {

    public Fastbond(UUID ownerId) {
        super(ownerId, 101, "Fastbond", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "LEA";

        this.color.setGreen(true);

        // You may play any number of additional lands on each of your turns.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayAdditionalLandsControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield)));
        // Whenever you play a land, if it wasn't the first land you played this turn, Fastbond deals 1 damage to you.
        this.addAbility(new PlayALandTriggeredAbility());
    }
    

    public Fastbond(final Fastbond card) {
        super(card);
    }

    @Override
    public Fastbond copy() {
        return new Fastbond(this);
    }
}


class PlayALandTriggeredAbility extends TriggeredAbilityImpl<PlayALandTriggeredAbility> {



    public PlayALandTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageControllerEffect(1), false);
    }

    public PlayALandTriggeredAbility(PlayALandTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LAND_PLAYED && event.getPlayerId() == this.getControllerId()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player player = game.getPlayer(this.getControllerId());
        if(player != null){
            if(player.getLandsPlayed() != 1){
                return true;
            }
        }
        return false;
    }

    @Override
    public PlayALandTriggeredAbility copy() {
        return new PlayALandTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you play a land, if it wasn't the first land you played this turn, {source} deals 1 damage to you";
    }
    
    
}


