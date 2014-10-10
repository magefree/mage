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
package mage.sets.magic2015;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AEtherspouts extends CardImpl {

    public AEtherspouts(UUID ownerId) {
        super(ownerId, 44, "AEtherspouts", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");
        this.expansionSetCode = "M15";

        this.color.setBlue(true);

        // For each attacking creature, its owner puts it on the top or bottom of his or her library.
        this.getSpellAbility().addEffect(new AEtherspoutsEffect());
    }

    public AEtherspouts(final AEtherspouts card) {
        super(card);
    }

    @Override
    public AEtherspouts copy() {
        return new AEtherspouts(this);
    }
}

/*
7/18/2014 	The owner of each attacking creature chooses whether to put it on the top or bottom
            of his or her library. The active player (the player whose turn it is) makes all of
            his or her choices first, followed by each other player in turn order.
7/18/2014 	If an effect puts two or more cards on the top or bottom of a library at the same time,
            the owner of those cards may arrange them in any order. That library’s owner doesn’t reveal
            the order in which the cards go into his or her library.
*/
class AEtherspoutsEffect extends OneShotEffect {

    public AEtherspoutsEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each attacking creature, its owner puts it on the top or bottom of his or her library";
    }

    public AEtherspoutsEffect(final AEtherspoutsEffect effect) {
        super(effect);
    }

    @Override
    public AEtherspoutsEffect copy() {
        return new AEtherspoutsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = true;
        game.getPlayerList();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent permanent:game.getState().getBattlefield().getActivePermanents(new FilterAttackingCreature(), source.getControllerId(), source.getSourceId(), game)) {
                Player owner = game.getPlayer(permanent.getOwnerId());
                if (owner != null) {
                    owner.moveCardToLibraryWithInfo(permanent, source.getSourceId(), game, Zone.BATTLEFIELD,
                            owner.chooseUse(Outcome.ReturnToHand, "Put " + permanent.getLogName() + " to the top? (else it goes to bottom)", game), true);
                } else {
                    result = false;
                }
            }
            return result;


//            PlayerList playerList = game.getPlayerList();
//            playerList.setCurrent(game.getActivePlayerId());
//            Player player = game.getPlayer(game.getActivePlayerId());
//            do {
//                player = playerList.getNext(game);
            
//            } while (!player.getId().equals(game.getActivePlayerId()));

        }
        return false;
    }
}
