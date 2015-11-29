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
package mage.abilities.effects.common.continuous;

import java.util.ArrayList;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public class PlayWithHandRevealedEffect extends ContinuousEffectImpl {

    private TargetController who;

    public PlayWithHandRevealedEffect(TargetController who) {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
        this.who = who;
    }

    public PlayWithHandRevealedEffect(final PlayWithHandRevealedEffect effect) {
        super(effect);
        who = effect.who;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Iterable<UUID> affectedPlayers;
            switch(who) {
                case ANY:
                    affectedPlayers = controller.getInRange();
                    break;
                case OPPONENT:
                    affectedPlayers = game.getOpponents(source.getControllerId());
                    break;
                case YOU:
                    ArrayList tmp = new ArrayList<UUID>();
                    tmp.add(source.getControllerId());
                    affectedPlayers = tmp;
                    break;
                default:
                    return false;
            }

            for(UUID playerID: affectedPlayers) {
                Player player = game.getPlayer(playerID);
                if (player != null) {
                    player.revealCards(player.getName() + "'s hand cards", player.getHand(), game, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PlayWithHandRevealedEffect copy() {
        return new PlayWithHandRevealedEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        switch(who) {
            case ANY:
                return "Players play with their hands revealed";
            case OPPONENT:
                return "Your opponents play with their hands revealed";
            case YOU:
                return "Play with your hand revealed";
            default:
                return "Unknown TargetController for PlayWithHandRevealedEffect";
        }
    }
}
