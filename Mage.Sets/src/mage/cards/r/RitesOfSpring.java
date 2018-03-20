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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class RitesOfSpring extends CardImpl {

    public RitesOfSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Discard any number of cards. Search your library for up to that many basic land cards, reveal those cards, and put them into your hand. Then shuffle your library.
        getSpellAbility().addEffect(new RitesOfSpringEffect());
    }

    public RitesOfSpring(final RitesOfSpring card) {
        super(card);
    }

    @Override
    public RitesOfSpring copy() {
        return new RitesOfSpring(this);
    }
}

class RitesOfSpringEffect extends OneShotEffect {

    public RitesOfSpringEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Discard any number of cards. Search your library for up to that many basic land cards, reveal those cards, and put them into your hand. Then shuffle your library.";
    }

    public RitesOfSpringEffect(final RitesOfSpringEffect effect) {
        super(effect);
    }

    @Override
    public RitesOfSpringEffect copy() {
        return new RitesOfSpringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInHand(0, Integer.MAX_VALUE, new FilterCard("cards to discard"));
            while (controller.canRespond() && !target.isChosen()) {
                target.choose(Outcome.BoostCreature, controller.getId(), source.getSourceId(), game);
            }
            int numDiscarded = 0;
            for (UUID targetId : target.getTargets()) {
                Card card = controller.getHand().get(targetId, game);
                if (controller.discard(card, source, game)) {
                    numDiscarded++;
                }
            }
            game.applyEffects();
            return new SearchLibraryPutInHandEffect(
                    new TargetCardInLibrary(0, numDiscarded, StaticFilters.FILTER_BASIC_LAND_CARD), true, true)
                    .apply(game, source);
        }
        return false;
    }
}
