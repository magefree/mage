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
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ScourgeOfNumai extends CardImpl {

    public ScourgeOfNumai(UUID ownerId) {
        super(ownerId, 80, "Scourge of Numai", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Demon");
        this.subtype.add("Spirit");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you lose 2 life if you don't control an Ogre.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ScourgeOfNumaiEffect(), TargetController.YOU, false));        
    }

    public ScourgeOfNumai(final ScourgeOfNumai card) {
        super(card);
    }

    @Override
    public ScourgeOfNumai copy() {
        return new ScourgeOfNumai(this);
    }
}

class ScourgeOfNumaiEffect extends OneShotEffect {
    
    public ScourgeOfNumaiEffect() {
        super(Outcome.LoseLife);
        this.staticText = "you lose 2 life if you don't control an Ogre.";
    }
    
    public ScourgeOfNumaiEffect(final ScourgeOfNumaiEffect effect) {
        super(effect);
    }
    
    @Override
    public ScourgeOfNumaiEffect copy() {
        return new ScourgeOfNumaiEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (game.getBattlefield().countAll(new FilterCreaturePermanent("Ogre", "Ogre"), source.getControllerId(), game) < 1) {
                controller.loseLife(2, game);
            }
            return true;
        }
        return false;
    }
}
