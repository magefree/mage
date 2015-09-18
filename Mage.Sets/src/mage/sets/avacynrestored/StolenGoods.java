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

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author noxx
 */
public class StolenGoods extends CardImpl {

    public StolenGoods(UUID ownerId) {
        super(ownerId, 78, "Stolen Goods", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{U}");
        this.expansionSetCode = "AVR";

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

class StolenGoodsEffect extends OneShotEffect {

    public StolenGoodsEffect() {
        super(Outcome.Detriment);
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
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (opponent != null && opponent.getLibrary().size() > 0 && sourceObject != null) {
            Library library = opponent.getLibrary();
            Card card;
            do {
                card = library.removeFromTop(game);
                if (card != null) {
                    opponent.moveCardsToExile(card, source, game, true, source.getSourceId(), sourceObject.getIdName());
                }
            } while (library.size() > 0 && card != null && card.getCardType().contains(CardType.LAND));

            if (card != null) {
                ContinuousEffect effect = new StolenGoodsCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class StolenGoodsCastFromExileEffect extends AsThoughEffectImpl {

    public StolenGoodsCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast card from exile";
    }

    public StolenGoodsCastFromExileEffect(final StolenGoodsCastFromExileEffect effect) {
        super(effect);
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
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId != null && sourceId.equals(getTargetPointer().getFirst(game, source))
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(sourceId);
            if (card != null && game.getState().getZone(sourceId) == Zone.EXILED) {
                Player player = game.getPlayer(affectedControllerId);
                player.setCastSourceIdWithAlternateMana(sourceId, null);
                return true;
            }
        }
        return false;
    }
}
