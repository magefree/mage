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

package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public class GitaxianProbe extends CardImpl<GitaxianProbe> {

    public GitaxianProbe (UUID ownerId) {
        super(ownerId, 35, "Gitaxian Probe", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{UP}");
        this.expansionSetCode = "NPH";
        this.color.setBlue(true);

        // Look at target player's hand.
        this.getSpellAbility().addEffect(new GitaxianProbeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardControllerEffect(1));
        
    }

    public GitaxianProbe (final GitaxianProbe card) {
        super(card);
    }

    @Override
    public GitaxianProbe copy() {
        return new GitaxianProbe(this);
    }

}

class GitaxianProbeEffect extends OneShotEffect<GitaxianProbeEffect> {
    GitaxianProbeEffect() {
        super(Outcome.DrawCard);
        staticText = "Look at target player's hand";
    }

    GitaxianProbeEffect(final GitaxianProbeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null && controller != null) {
            controller.lookAtCards("Gitaxian Probe", player.getHand(), game);
        }
        return true;
    }

    @Override
    public GitaxianProbeEffect copy() {
        return new GitaxianProbeEffect(this);
    }

}
