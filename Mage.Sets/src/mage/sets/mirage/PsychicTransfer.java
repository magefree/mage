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
package mage.sets.mirage;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Quercitron
 */
public class PsychicTransfer extends CardImpl<PsychicTransfer> {

    public PsychicTransfer(UUID ownerId) {
        super(ownerId, 85, "Psychic Transfer", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{U}");
        this.expansionSetCode = "MIR";

        this.color.setBlue(true);

        // If the difference between your life total and target player's life total is 5 or less, exchange life totals with that player.
        this.getSpellAbility().addEffect(new PsychicTransferEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(true));
    }

    public PsychicTransfer(final PsychicTransfer card) {
        super(card);
    }

    @Override
    public PsychicTransfer copy() {
        return new PsychicTransfer(this);
    }
}

class PsychicTransferEffect extends OneShotEffect<PsychicTransferEffect> 
{

    public PsychicTransferEffect() {
        super(Outcome.Neutral);
        this.staticText = "If the difference between your life total and target player's life total is 5 or less, exchange life totals with that player";
    }

    public PsychicTransferEffect(final PsychicTransferEffect effect) {
        super(effect);
    }

    @Override
    public PsychicTransferEffect copy() {
        return new PsychicTransferEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getTargets().getFirstTarget());
        if (sourcePlayer != null && targetPlayer != null) {
            int lifePlayer1 = sourcePlayer.getLife();
            int lifePlayer2 = targetPlayer.getLife();

            if (Math.abs(lifePlayer1 - lifePlayer2) > 5) {
                return false;
            }
            
            if (lifePlayer1 == lifePlayer2) {
                return false;
            }

            // 20110930 - 118.7, 118.8
            if (lifePlayer1 < lifePlayer2 && (!sourcePlayer.isCanGainLife() || !targetPlayer.isCanLoseLife())) {
                return false;
            }

            if (lifePlayer1 > lifePlayer2 && (!sourcePlayer.isCanLoseLife() || !targetPlayer.isCanGainLife())) {
                return false;
            }

            sourcePlayer.setLife(lifePlayer2, game);
            targetPlayer.setLife(lifePlayer1, game);
            return true;
        }
        return false;
    }
}
