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
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class ReturnOfTheNightstalkers extends CardImpl {

    public ReturnOfTheNightstalkers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // Return all Nightstalker permanent cards from your graveyard to the battlefield. Then destroy all Swamps you control.
        this.getSpellAbility().addEffect(new ReturnOfTheNightstalkersEffect());
    }

    public ReturnOfTheNightstalkers(final ReturnOfTheNightstalkers card) {
        super(card);
    }

    @Override
    public ReturnOfTheNightstalkers copy() {
        return new ReturnOfTheNightstalkers(this);
    }
}

class ReturnOfTheNightstalkersEffect extends OneShotEffect {

    private static final FilterPermanentCard filter1 = new FilterPermanentCard();
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent();

    static {
        filter1.add(new SubtypePredicate(SubType.NIGHTSTALKER));
        filter2.add(new SubtypePredicate(SubType.SWAMP));
    }

    public ReturnOfTheNightstalkersEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Return all Nightstalker permanent cards from your graveyard to the battlefield. Then destroy all Swamps you control.";
    }

    public ReturnOfTheNightstalkersEffect(final ReturnOfTheNightstalkersEffect effect) {
        super(effect);
    }

    @Override
    public ReturnOfTheNightstalkersEffect copy() {
        return new ReturnOfTheNightstalkersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Card card : player.getGraveyard().getCards(filter1, game)) {
                card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), source.getControllerId());
            }
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter2, source.getControllerId(), source.getSourceId(), game)) {
                permanent.destroy(source.getSourceId(), game, false);
            }
        }
        return true;
    }
}
