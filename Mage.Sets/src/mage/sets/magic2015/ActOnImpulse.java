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
package mage.sets.magic2015;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
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

/**
 *
 * @author Quercitron
 */
public class ActOnImpulse extends CardImpl {

    public ActOnImpulse(UUID ownerId) {
        super(ownerId, 126, "Act on Impulse", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{R}");
        this.expansionSetCode = "M15";


        // Exile the top three cards of your library. Until end of turn, you may play cards exiled this way.
        this.getSpellAbility().addEffect(new ActOnImpulseExileEffect());
    }

    public ActOnImpulse(final ActOnImpulse card) {
        super(card);
    }

    @Override
    public ActOnImpulse copy() {
        return new ActOnImpulse(this);
    }
}

class ActOnImpulseExileEffect extends OneShotEffect {
    
    public ActOnImpulseExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile the top three cards of your library. Until end of turn, you may play cards exiled this way.";
    }
    
    public ActOnImpulseExileEffect(final ActOnImpulseExileEffect effect) {
        super(effect);
    }

    @Override
    public ActOnImpulseExileEffect copy() {
        return new ActOnImpulseExileEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Library library = controller.getLibrary();
            List<Card> cards = new ArrayList<>();
            int count = Math.min(3, library.size());
            for (int i = 0; i < count; i++) {
                Card card = library.removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            }
            if (cards.size() > 0) {
                List<UUID> cardsId = new ArrayList<>();
                for (Card card : cards) {
                    card.moveToExile(source.getSourceId(), "Act on Impulse", source.getSourceId(), game);
                    cardsId.add(card.getId());                   
                }
                game.addEffect(new ActOnImpulseMayPlayExiledEffect(cardsId), source);
            }
            return true;
        }
        return false;
    }
    
}

class ActOnImpulseMayPlayExiledEffect extends AsThoughEffectImpl {

    public List<UUID> cards = new ArrayList<>();
    
    public ActOnImpulseMayPlayExiledEffect(List<UUID> cards) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.cards.addAll(cards);
    }
    
    public ActOnImpulseMayPlayExiledEffect(final ActOnImpulseMayPlayExiledEffect effect) {
        super(effect);
        this.cards.addAll(effect.cards);
    }

    @Override
    public ActOnImpulseMayPlayExiledEffect copy() {
        return new ActOnImpulseMayPlayExiledEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(sourceId);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null && game.getState().getZone(sourceId) == Zone.EXILED) {
            if (cards.contains(sourceId)) {
                return true;
            }
        }
        return false;
    }
    
}
