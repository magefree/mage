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

import java.util.UUID;
import mage.Constants.AsThoughEffectType;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author BetaSteward
 */
public class PraetorsGrasp extends CardImpl<PraetorsGrasp> {

    public PraetorsGrasp(UUID ownerId) {
        super(ownerId, 71, "Praetor's Grasp", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");
        this.expansionSetCode = "NPH";

        this.color.setBlack(true);

        // Search target opponent's library for a card and exile it face down. Then that player shuffles his or her library. You may look at and play that card for as long as it remains exiled.
        this.getSpellAbility().addEffect(new PraetorsGraspEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        
    }

    public PraetorsGrasp(final PraetorsGrasp card) {
        super(card);
    }

    @Override
    public PraetorsGrasp copy() {
        return new PraetorsGrasp(this);
    }
}

class PraetorsGraspEffect extends OneShotEffect<PraetorsGraspEffect> {

    public PraetorsGraspEffect() {
        super(Outcome.PlayForFree);
        staticText = "Search target opponent's library for a card and exile it face down. Then that player shuffles his or her library. You may look at and play that card for as long as it remains exiled";
    }

    public PraetorsGraspEffect(final PraetorsGraspEffect effect) {
        super(effect);
    }

    @Override
    public PraetorsGraspEffect copy() {
        return new PraetorsGraspEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && opponent != null) {
            Cards opponentLibrary = new CardsImpl();
            opponentLibrary.addAll(opponent.getLibrary().getCardList());

            TargetCardInLibrary target = new TargetCardInLibrary();
            if (player.choose(Outcome.Benefit, opponentLibrary, target, game)) {
                UUID targetId = target.getFirstTarget();
                Card card = opponent.getLibrary().remove(targetId, game);
                if (card != null) {
                    card.setFaceDown(true);
                    card.moveToExile(getId(), "Praetor's Grasp", source.getSourceId(), game);
                    game.addEffect(new PraetorsGraspPlayEffect(card.getId()), source);
                }
            }
        }

        opponent.shuffleLibrary(game);
        return true;
    }
}

class PraetorsGraspPlayEffect extends AsThoughEffectImpl<PraetorsGraspPlayEffect> {

    private UUID cardId;
    
	public PraetorsGraspPlayEffect(UUID cardId) {
		super(AsThoughEffectType.CAST, Duration.EndOfGame, Outcome.Benefit);
        this.cardId = cardId;
		staticText = "You may look at and play that card for as long as it remains exiled";
	}

	public PraetorsGraspPlayEffect(final PraetorsGraspPlayEffect effect) {
		super(effect);
        this.cardId = effect.cardId;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public PraetorsGraspPlayEffect copy() {
		return new PraetorsGraspPlayEffect(this);
	}

	@Override
	public boolean applies(UUID sourceId, Ability source, Game game) {
        if (sourceId.equals(cardId)) {
            Card card = game.getCard(cardId);
            if (card != null && game.getState().getZone(cardId) == Zone.EXILED) {
                return true;
            }
        }
        return false;
	}

}