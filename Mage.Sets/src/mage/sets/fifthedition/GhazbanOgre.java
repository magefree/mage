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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.game.permanent.Permanent;

/**
 *
 * @author MarcoMarin, Watch out! This one I actually made from scratch!(1st time \o/) Not even checked similars :) beware!
 */
public class GhazbanOgre extends CardImpl {

    public GhazbanOgre(UUID ownerId) {
        super(ownerId, 160, "Ghazban Ogre", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "5ED";
        this.subtype.add("Ogre");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, if a player has more life than each other player, the player with the most life gains control of Ghazb&aacute;n Ogre.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GhazbanOgreEffect(), TargetController.YOU, false));
        
    }

    public GhazbanOgre(final GhazbanOgre card) {
        super(card);
    }

    @Override
    public GhazbanOgre copy() {
        return new GhazbanOgre(this);
    }
}
class GhazbanOgreEffect extends OneShotEffect {
    
    public GhazbanOgreEffect() {
        super(Outcome.GainControl);
        this.staticText = "the player with the most life gains control of Ghazban Ogre";
    }
    
    public GhazbanOgreEffect(final GhazbanOgreEffect effect) {
        super(effect);
    }
    
    @Override
    public GhazbanOgreEffect copy() {
        return new GhazbanOgreEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player newOwner = null;
        int lowLife = Integer.MIN_VALUE;
        boolean tie = false;
        for (UUID playerID : game.getPlayerList()){
            Player player = game.getPlayer(playerID);        
            if (player.getLife() > lowLife){
                lowLife = player.getLife();
                newOwner = player;
                tie = false;
            }else if (player.getLife() == lowLife){ 
                tie = true;
            }        
        }
        if (!tie){
           game.getPermanent(source.getId()).changeControllerId(newOwner.getId(), game);
        }
        
        return true;
                        
    }
}