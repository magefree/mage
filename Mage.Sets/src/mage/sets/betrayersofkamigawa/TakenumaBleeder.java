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
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class TakenumaBleeder extends CardImpl {

    public TakenumaBleeder(UUID ownerId) {
        super(ownerId, 86, "Takenuma Bleeder", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Ogre");
        this.subtype.add("Shaman");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Takenuma Bleeder attacks or blocks, you lose 1 life if you don't control a Demon.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new TakenumaBleederEffect(), false));
        
    }

    public TakenumaBleeder(final TakenumaBleeder card) {
        super(card);
    }

    @Override
    public TakenumaBleeder copy() {
        return new TakenumaBleeder(this);
    }
}

class TakenumaBleederEffect extends OneShotEffect {
    
    public TakenumaBleederEffect() {
        super(Outcome.LoseLife);
        this.staticText = "you lose 1 life if you don't control a Demon";
    }
    
    public TakenumaBleederEffect(final TakenumaBleederEffect effect) {
        super(effect);
    }
    
    @Override
    public TakenumaBleederEffect copy() {
        return new TakenumaBleederEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (game.getBattlefield().countAll(new FilterCreaturePermanent("Demon", "Demon"), source.getControllerId(), game) < 1) {
                controller.loseLife(1, game);
            }
            return true;
        }
        return false;
    }
}
