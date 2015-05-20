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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class WeedStrangle extends CardImpl {

    public WeedStrangle(UUID ownerId) {
        super(ownerId, 147, "Weed Strangle", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");
        this.expansionSetCode = "LRW";


        // Destroy target creature. Clash with an opponent. If you win, you gain life equal to that creature's toughness.
        this.getSpellAbility().addEffect(new WeedStrangleEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    public WeedStrangle(final WeedStrangle card) {
        super(card);
    }

    @Override
    public WeedStrangle copy() {
        return new WeedStrangle(this);
    }
}

class WeedStrangleEffect extends OneShotEffect {

    public WeedStrangleEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature. Clash with an opponent. If you win, you gain life equal to that creature's toughness";
    }

    public WeedStrangleEffect(final WeedStrangleEffect effect) {
        super(effect);
    }

    @Override
    public WeedStrangleEffect copy() {
        return new WeedStrangleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && creature != null) {
            creature.destroy(source.getSourceId(), game, false);
            if (ClashEffect.getInstance().apply(game, source)) {
                controller.gainLife(creature.getToughness().getValue(), game);
            }
            return true;
        }
        return false;
    }
}
