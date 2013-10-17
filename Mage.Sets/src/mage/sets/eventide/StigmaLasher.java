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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class StigmaLasher extends CardImpl<StigmaLasher> {
    
    public StigmaLasher(UUID ownerId) {
        super(ownerId, 62, "Stigma Lasher", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}{R}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Elemental");
        this.subtype.add("Shaman");
        
        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Wither
        this.addAbility(WitherAbility.getInstance());

        // Whenever Stigma Lasher deals damage to a player, that player can't gain life for the rest of the game.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new StigmaLasherEffect(), false, true));
        
    }
    
    public StigmaLasher(final StigmaLasher card) {
        super(card);
    }
    
    @Override
    public StigmaLasher copy() {
        return new StigmaLasher(this);
    }
}

class StigmaLasherEffect extends ContinuousEffectImpl<StigmaLasherEffect> {
    
    public StigmaLasherEffect() {
        super(Duration.EndOfGame, Layer.PlayerEffects, SubLayer.NA, Outcome.Neutral);
        staticText = "that player can't gain life for the rest of the game";
    }
    
    public StigmaLasherEffect(final StigmaLasherEffect effect) {
        super(effect);
    }
    
    @Override
    public StigmaLasherEffect copy() {
        return new StigmaLasherEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.targetPointer.getFirst(game, source));
        if (player != null) {
            player.setCanGainLife(false);
        }
        return true;
    }
}