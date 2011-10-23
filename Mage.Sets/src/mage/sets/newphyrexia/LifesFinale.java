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
package mage.sets.newphyrexia;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public class LifesFinale extends CardImpl<LifesFinale> {

    public LifesFinale(UUID ownerId) {
        super(ownerId, 65, "Life's Finale", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");
        this.expansionSetCode = "NPH";

        this.color.setBlack(true);

        this.getSpellAbility().addEffect(new LifesFinaleEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public LifesFinale(final LifesFinale card) {
        super(card);
    }

    @Override
    public LifesFinale copy() {
        return new LifesFinale(this);
    }
}

class LifesFinaleEffect extends OneShotEffect {

    public LifesFinaleEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy all creatures, then search target opponent's library for up to three creature cards and put them into his or her graveyard. Then that player shuffles his or her library";
    }

    public LifesFinaleEffect(final LifesFinaleEffect effect) {
        super(effect);
    }

    @Override
    public LifesFinaleEffect copy() {
        return new LifesFinaleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            permanent.destroy(source.getId(), game, false);
        }

        Player opponent = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && opponent != null) {
            Cards opponentLibrary = new CardsImpl();
            opponentLibrary.addAll(opponent.getLibrary().getCardList());

            TargetCardInLibrary target = new TargetCardInLibrary(0, 3, new FilterCreatureCard("creature cards from his library to put in his graveyard"));
            if (player.choose(Outcome.Benefit, opponentLibrary, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = opponent.getLibrary().remove(targetId, game);
                    if (card != null) {
                        card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                    }
                }
            }
        }

        opponent.shuffleLibrary(game);
        return true;
    }
}
