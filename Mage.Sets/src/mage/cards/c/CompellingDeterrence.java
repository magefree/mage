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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author fireshoes
 */
public class CompellingDeterrence extends CardImpl {

    public CompellingDeterrence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent to its owner's hand. Then that player discards a card if you control a Zombie.
        this.getSpellAbility().addEffect(new CompellingDeterrenceEffect());
        getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    public CompellingDeterrence(final CompellingDeterrence card) {
        super(card);
    }

    @Override
    public CompellingDeterrence copy() {
        return new CompellingDeterrence(this);
    }
}

class CompellingDeterrenceEffect extends OneShotEffect {

    public CompellingDeterrenceEffect() {
        super(Outcome.Detriment);
        this.staticText = "return target nonland permanent to its owner's hand. Then that player discards a card if you control a Zombie";
    }

    public CompellingDeterrenceEffect(final CompellingDeterrenceEffect effect) {
        super(effect);
    }

    @Override
    public CompellingDeterrenceEffect copy() {
        return new CompellingDeterrenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(target.getControllerId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && player != null) {
            player.moveCards(target, Zone.HAND, source, game);
            game.applyEffects();
            FilterPermanent FILTER = new FilterPermanent();
            FILTER.add(new SubtypePredicate(SubType.ZOMBIE));
            if (game.getState().getBattlefield().countAll(FILTER, controller.getId(), game) > 0) {
                player.discard(1, false, source, game);
            }
            return true;
        }
        return false;
    }
}
