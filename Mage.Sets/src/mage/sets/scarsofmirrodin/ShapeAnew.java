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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author ayratn
 */
public class ShapeAnew extends CardImpl<ShapeAnew> {

	private static final FilterPermanent filter = new FilterPermanent("an artifact");

	static {
		filter.getCardType().add(CardType.ARTIFACT);
	}

    public ShapeAnew (UUID ownerId) {
        super(ownerId, 43, "Shape Anew", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{U}");
        this.expansionSetCode = "SOM";
        this.color.setBlue(true);
        this.getSpellAbility().addEffect(new SacrificeTargetEffect());
		this.getSpellAbility().addTarget(new TargetPermanent(filter));
		this.getSpellAbility().addEffect(new ShapeAnewEffect());
    }

    public ShapeAnew (final ShapeAnew card) {
        super(card);
    }

    @Override
    public ShapeAnew copy() {
        return new ShapeAnew(this);
    }

	private class ShapeAnewEffect extends OneShotEffect<ShapeAnewEffect> {

		public ShapeAnewEffect() {
			super(Constants.Outcome.PutCardInPlay);
            staticText = "Then reveals cards from the top of his or her library until he or she reveals an artifact card. That player puts that card onto the battlefield, then shuffles all other cards revealed this way into his or her library";
		}

		public ShapeAnewEffect(ShapeAnewEffect effect) {
			super(effect);
		}

		@Override
		public boolean apply(Game game, Ability source) {
            Permanent sourcePermanent = (Permanent) game.getLastKnownInformation(targetPointer.getFirst(source), Constants.Zone.BATTLEFIELD);
            if (sourcePermanent == null) {
                return false;
            }
			Player controller = game.getPlayer(source.getControllerId());
			if (controller == null) {
				return false;
			}
			Cards revealed = new CardsImpl();
			Card artifactCard = null;
			Cards nonArtifactCards = new CardsImpl();
			Player player = game.getPlayer(sourcePermanent.getControllerId());
			while (artifactCard == null && player.getLibrary().size() > 0) {
				Card card = player.getLibrary().removeFromTop(game);
				revealed.add(card);
				if (card.getCardType().contains(CardType.ARTIFACT))
					artifactCard = card;
				else
					nonArtifactCards.add(card);
			}
			player.revealCards("Shape Anew", revealed, game);
			artifactCard.putOntoBattlefield(game, Constants.Zone.LIBRARY, source.getId(), player.getId());
			player.getLibrary().addAll(nonArtifactCards.getCards(game), game);
			player.shuffleLibrary(game);
			return true;
		}

		@Override
		public ShapeAnewEffect copy() {
			return new ShapeAnewEffect(this);
		}
	}
}
