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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author noxx
 */
public class StolenGoods extends CardImpl<StolenGoods> {

    public StolenGoods(UUID ownerId) {
        super(ownerId, 78, "Stolen Goods", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{U}");
        this.expansionSetCode = "AVR";

        this.color.setBlue(true);

        // Target opponent exiles cards from the top of his or her library until he or she exiles a nonland card. Until end of turn, you may cast that card without paying its mana cost.
        this.getSpellAbility().addEffect(new StolenGoodsEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public StolenGoods(final StolenGoods card) {
        super(card);
    }

    @Override
    public StolenGoods copy() {
        return new StolenGoods(this);
    }
}

class StolenGoodsEffect extends OneShotEffect<StolenGoodsEffect> {

    public StolenGoodsEffect() {
        super(Constants.Outcome.Detriment);
        this.staticText = "Target opponent exiles cards from the top of his or her library until he or she exiles a nonland card. Until end of turn, you may cast that card without paying its mana cost";
    }

    public StolenGoodsEffect(final StolenGoodsEffect effect) {
        super(effect);
    }

    @Override
    public StolenGoodsEffect copy() {
        return new StolenGoodsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null && player.getLibrary().size() > 0) {
            Library library = player.getLibrary();
            Card card;
            do {
                card = library.removeFromTop(game);
                if (card != null) {
                    card.moveToExile(source.getId(), "Stolen Goods", source.getSourceId(), game);
                }
            } while (library.size() > 0 && card != null && card.getCardType().contains(CardType.LAND));

            if (card != null) {
                player.revealCards("Card to cast", new CardsImpl(card), game);
                game.addEffect(new StolenGoodsCastFromExileEffect(card.getId()), source);
            }
            return true;
        }
        return false;
    }
}

class StolenGoodsCastFromExileEffect extends AsThoughEffectImpl<StolenGoodsCastFromExileEffect> {

    private UUID cardId;

    public StolenGoodsCastFromExileEffect(UUID cardId) {
        super(Constants.AsThoughEffectType.CAST, Constants.Duration.EndOfTurn, Constants.Outcome.Benefit);
        staticText = "You may cast card from exile";
        this.cardId = cardId;
    }

    public StolenGoodsCastFromExileEffect(final StolenGoodsCastFromExileEffect effect) {
        super(effect);
        cardId = effect.cardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public StolenGoodsCastFromExileEffect copy() {
        return new StolenGoodsCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (sourceId.equals(this.cardId)) {
            Card card = game.getCard(this.cardId);
            if (card != null && game.getState().getZone(this.cardId) == Constants.Zone.EXILED) {
                Player player = game.getPlayer(source.getControllerId());
                if (player != null && player.chooseUse(Constants.Outcome.Benefit, "Cast the card without paying cost?", game)) {
                    player.cast(card.getSpellAbility(), game, true);
                }
                return false;
            }
        }
        return false;
    }
}
