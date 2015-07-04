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
package mage.sets.commander;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class WhirlpoolWhelm extends CardImpl {

    public WhirlpoolWhelm(UUID ownerId) {
        super(ownerId, 69, "Whirlpool Whelm", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "CMD";


        // Clash with an opponent, then return target creature to its owner's hand. If you win, you may put that creature on top of its owner's library instead.
        this.getSpellAbility().addEffect(new WhirlpoolWhelmEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public WhirlpoolWhelm(final WhirlpoolWhelm card) {
        super(card);
    }

    @Override
    public WhirlpoolWhelm copy() {
        return new WhirlpoolWhelm(this);
    }
}

class WhirlpoolWhelmEffect extends OneShotEffect {

    public WhirlpoolWhelmEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Clash with an opponent, then return target creature to its owner's hand. If you win, you may put that creature on top of its owner's library instead";
    }

    public WhirlpoolWhelmEffect(final WhirlpoolWhelmEffect effect) {
        super(effect);
    }

    @Override
    public WhirlpoolWhelmEffect copy() {
        return new WhirlpoolWhelmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null) {
            boolean topOfLibrary = false;
            if (ClashEffect.getInstance().apply(game, source)) {
                topOfLibrary = controller.chooseUse(outcome, "Put " + creature.getLogName() + " to top of libraray instead?" , source, game);
            }
            if (topOfLibrary) {
                controller.moveCardToHandWithInfo(creature, source.getSourceId(), game, Zone.BATTLEFIELD);
            } else {
                controller.moveCardToLibraryWithInfo(creature, source.getSourceId(), game, Zone.BATTLEFIELD, true, true);
            }
            return true;
        }
        return false;
    }
}