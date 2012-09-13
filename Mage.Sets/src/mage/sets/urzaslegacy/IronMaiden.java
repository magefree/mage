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
package mage.sets.urzaslegacy;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class IronMaiden extends CardImpl<IronMaiden> {

    public IronMaiden(UUID ownerId) {
        super(ownerId, 127, "Iron Maiden", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "ULG";

        // At the beginning of each opponent's upkeep, Iron Maiden deals X damage to that player, where X is the number of cards in his or her hand minus 4.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new IronMaidenEffect(), Constants.TargetController.OPPONENT, false);
        this.addAbility(ability);
    }

    public IronMaiden(final IronMaiden card) {
        super(card);
    }

    @Override
    public IronMaiden copy() {
        return new IronMaiden(this);
    }
    
    
}


class IronMaidenEffect extends OneShotEffect<IronMaidenEffect> {

    private IronMaidenEffect(final IronMaidenEffect effect) {
        super(effect);
    }

    public IronMaidenEffect() {
        super(Constants.Outcome.Damage);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if(player != null)
        {
            int amount = player.getHand().size() - 4;
            if(amount > 0)
            {
                if (player != null) {
                    player.damage(amount, source.getSourceId(), game, false, true);
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    public IronMaidenEffect copy() {
        return new IronMaidenEffect(this);
    }
    
    @Override
    public String getText(Mode mode) {
        return "Iron Maiden deals X damage to that player, where X is the number of cards in his or her hand minus 4";
    }

}
