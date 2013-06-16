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
package mage.sets.mirrodin;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.cards.CardImpl;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class FieryGambit extends CardImpl<FieryGambit> {

    public FieryGambit(UUID ownerId) {
        super(ownerId, 90, "Fiery Gambit", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{R}");
        this.expansionSetCode = "MRD";

        this.color.setRed(true);

        // Flip a coin until you lose a flip or choose to stop flipping. If you lose a flip, Fiery Gambit has no effect. If you win one or more flips, Fiery Gambit deals 3 damage to target creature. If you win two or more flips, Fiery Gambit deals 6 damage to each opponent. If you win three or more flips, draw nine cards and untap all lands you control.
        this.getSpellAbility().addEffect(new FieryGambitEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
    }

    public FieryGambit(final FieryGambit card) {
        super(card);
    }

    @Override
    public FieryGambit copy() {
        return new FieryGambit(this);
    }
}


class FieryGambitEffect extends OneShotEffect<FieryGambitEffect> {

    public FieryGambitEffect() {
        super(Outcome.Benefit);
        this.staticText = "Flip a coin until you lose a flip or choose to stop flipping. If you lose a flip, Fiery Gambit has no effect. If you win one or more flips, Fiery Gambit deals 3 damage to target creature. If you win two or more flips, Fiery Gambit deals 6 damage to each opponent. If you win three or more flips, draw nine cards and untap all lands you control";
    }

    public FieryGambitEffect(final FieryGambitEffect effect) {
        super(effect);
    }

    @Override
    public FieryGambitEffect copy() {
        return new FieryGambitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int flipsWon = 0;
            boolean controllerStopped = false;
            while (controller.flipCoin(game)) {
                ++flipsWon;
                if (!controller.chooseUse(outcome, new StringBuilder("You won ").append(flipsWon).append(flipsWon == 1?" flip.":" flips.")
                        .append(" Flip another coin?").toString(), game)) {
                    controllerStopped = true;
                    break;
                }
            }
            if (controllerStopped) {
                Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (creature != null) {
                    creature.damage(3, source.getSourceId(), game, true, false);
                }
                if (flipsWon > 1) {
                    new DamagePlayersEffect(6, TargetController.OPPONENT).apply(game, source);
                }
                if (flipsWon > 2) {
                    controller.drawCards(9, game);
                    new UntapAllLandsControllerEffect().apply(game, source);
                }
            } else {
                game.informPlayers("Fiery Gambit had no effect");
            }
            return true;
        }
        return false;
    }
}
