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
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class NoeticScales extends CardImpl {

    public NoeticScales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of each player's upkeep, return to its owner's hand each creature that player controls with power greater than the number of cards in his or her hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new NoeticScalesEffect(), TargetController.ANY, false, true));
    }

    public NoeticScales(final NoeticScales card) {
        super(card);
    }

    @Override
    public NoeticScales copy() {
        return new NoeticScales(this);
    }
}

class NoeticScalesEffect extends OneShotEffect {

    public NoeticScalesEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return to its owner's hand each creature that player controls with power greater than the number of cards in his or her hand";
    }

    public NoeticScalesEffect(final NoeticScalesEffect effect) {
        super(effect);
    }

    @Override
    public NoeticScalesEffect copy() {
        return new NoeticScalesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int numberOfCardsInHand = player.getHand().size();
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                if (creature.getPower().getValue() > numberOfCardsInHand) {
                    if (creature.moveToZone(Zone.HAND, source.getId(), game, false)) {
                        game.informPlayers(player.getLogName() + " moves " + creature.getLogName() + " from the battlefield to their hand.");
                    }
                }
            }
            return true;
        }
        return false;
    }
}
