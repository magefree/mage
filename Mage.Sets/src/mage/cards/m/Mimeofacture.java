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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public class Mimeofacture extends CardImpl {

    public Mimeofacture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Replicate {3}{U}
        this.addAbility(new ReplicateAbility(this, "{3}{U}"));

        // Choose target permanent an opponent controls. Search that player's library for a card with the same name and put it onto the battlefield under your control. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new MimeofactureEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT));
    }

    public Mimeofacture(final Mimeofacture card) {
        super(card);
    }

    @Override
    public Mimeofacture copy() {
        return new Mimeofacture(this);
    }
}

class MimeofactureEffect extends OneShotEffect {

    MimeofactureEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Choose target permanent an opponent controls. "
                + "Search that player's library for a card with the same name and put it onto the battlefield under your control. "
                + "Then that player shuffles their library.";
    }

    MimeofactureEffect(final MimeofactureEffect effect) {
        super(effect);
    }

    @Override
    public MimeofactureEffect copy() {
        return new MimeofactureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || permanent == null) {
            return false;
        }
        Player opponent = game.getPlayer(permanent.getControllerId());
        if (opponent == null) {
            return false;
        }
        FilterCard filter = new FilterCard("card named " + permanent.getName());
        filter.add(new NamePredicate(permanent.getName()));
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
        if (controller.searchLibrary(target, game, opponent.getId())) {
            Card card = opponent.getLibrary().getCard(target.getFirstTarget(), game);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        opponent.shuffleLibrary(source, game);
        return true;
    }
}
