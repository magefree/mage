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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class Soulquake extends CardImpl<Soulquake> {

    public Soulquake(UUID ownerId) {
        super(ownerId, 30, "Soulquake", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{U}{U}{B}{B}");
        this.expansionSetCode = "ARB";

        this.color.setBlue(true);
        this.color.setBlack(true);

        // Return all creatures on the battlefield and all creature cards in graveyards to their owners' hands.
        this.getSpellAbility().addEffect(new SoulquakeEffect());

    }

    public Soulquake(final Soulquake card) {
        super(card);
    }

    @Override
    public Soulquake copy() {
        return new Soulquake(this);
    }
}

class SoulquakeEffect extends OneShotEffect<SoulquakeEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature");
    private static final FilterCreatureCard filter2 = new FilterCreatureCard("creature");

    public SoulquakeEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return all creatures on the battlefield and all creature cards in graveyards to their owners' hands";
    }

    public SoulquakeEffect(final SoulquakeEffect effect) {
        super(effect);
    }

    @Override
    public SoulquakeEffect copy() {
        return new SoulquakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            permanent.moveToZone(Constants.Zone.HAND, source.getSourceId(), game, true);
        }
        for (UUID playerId : game.getPlayer(source.getControllerId()).getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (Card card : player.getGraveyard().getCards(filter2, game)) {
                    card.moveToZone(Constants.Zone.HAND, source.getSourceId(), game, true);
                }
            }
        }
        return true;
    }
}