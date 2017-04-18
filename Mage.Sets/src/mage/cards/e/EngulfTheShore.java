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
package mage.cards.e;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class EngulfTheShore extends CardImpl {

    public EngulfTheShore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Return to their owners' hands all creatures with toughness less than or equal to the number of Islands you control.
        getSpellAbility().addEffect(new EngulfTheShoreEffect());
    }

    public EngulfTheShore(final EngulfTheShore card) {
        super(card);
    }

    @Override
    public EngulfTheShore copy() {
        return new EngulfTheShore(this);
    }
}

class EngulfTheShoreEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("number of Islands you control");

    static {
        filter.add(new SubtypePredicate("Island"));
    }

    public EngulfTheShoreEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return to their owners' hands all creatures with toughness less than or equal to the number of Islands you control";
    }

    public EngulfTheShoreEffect(final EngulfTheShoreEffect effect) {
        super(effect);
    }

    @Override
    public EngulfTheShoreEffect copy() {
        return new EngulfTheShoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int islands = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
            FilterPermanent creatureFilter = new FilterCreaturePermanent();
            creatureFilter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, islands + 1));
            Set<Card> cardsToHand = new HashSet<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(creatureFilter, source.getControllerId(), source.getSourceId(), game)) {
                cardsToHand.add(permanent);
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
