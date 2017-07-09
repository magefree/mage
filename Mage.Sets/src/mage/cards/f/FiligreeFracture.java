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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public class FiligreeFracture extends CardImpl {

    public FiligreeFracture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Destroy target artifact or enchantment. If that permanent was blue or black, draw a card.
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterArtifactOrEnchantmentPermanent()));
        this.getSpellAbility().addEffect(new FiligreeFractureEffect());
    }

    public FiligreeFracture(final FiligreeFracture card) {
        super(card);
    }

    @Override
    public FiligreeFracture copy() {
        return new FiligreeFracture(this);
    }
}

class FiligreeFractureEffect extends OneShotEffect {

    public FiligreeFractureEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact or enchantment. If that permanent was blue or black, draw a card";
    }

    public FiligreeFractureEffect(final FiligreeFractureEffect effect) {
        super(effect);
    }

    @Override
    public FiligreeFractureEffect copy() {
        return new FiligreeFractureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player != null && permanent != null) {
            permanent.destroy(source.getSourceId(), game, true);
            game.applyEffects();
            if (permanent.getColor(game).isBlack() || permanent.getColor(game).isBlue()) {
                player.drawCards(1, game);
            }
            return true;
        }
        return false;
    }
}
