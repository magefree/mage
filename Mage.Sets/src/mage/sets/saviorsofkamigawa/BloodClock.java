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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class BloodClock extends CardImpl<BloodClock> {

    public BloodClock(UUID ownerId) {
        super(ownerId, 153, "Blood Clock", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "SOK";

        // At the beginning of each player's upkeep, that player returns a permanent he or she controls to its owner's hand unless he or she pays 2 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new BloodClockEffect(), TargetController.ANY, false, true);
        this.addAbility(ability);
    }

    public BloodClock(final BloodClock card) {
        super(card);
    }

    @Override
    public BloodClock copy() {
        return new BloodClock(this);
    }
}

class BloodClockEffect extends OneShotEffect<BloodClockEffect> {

    public BloodClockEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "that player returns a permanent he or she controls to its owner's hand unless he or she pays 2 life";
    }

    public BloodClockEffect(final BloodClockEffect effect) {
        super(effect);
    }

    @Override
    public BloodClockEffect copy() {
        return new BloodClockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        if (player.getLife() > 2 && player.chooseUse(Outcome.Neutral, "Pay 2 life? If you don't, return a permanent you control to its owner's hand.", game)) {
            player.loseLife(2, game);
            game.informPlayers(player.getName() + " pays 2 life. He will not return a permanent he or she controls.");
            return true;
        } else {
            Target target = new TargetControlledPermanent();
            target.isRequired();
            if (target.canChoose(source.getSourceId(), player.getId(), game) && player.chooseTarget(outcome, target, source, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    game.informPlayers(player.getName() + " returns " + permanent.getName() + " to hand.");
                    return permanent.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                }
            }
        }
        return false;
    }
}
