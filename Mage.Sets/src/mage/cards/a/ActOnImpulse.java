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
package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author Quercitron
 */
public class ActOnImpulse extends CardImpl {

    public ActOnImpulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

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
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Set<Card> cards = new HashSet<>(controller.getLibrary().getTopCards(game, 3));
            if (!cards.isEmpty()) {
                controller.moveCardsToExile(cards, source, game, true, source.getSourceId(), sourceObject.getIdName());
                // remove cards that could not be moved to exile
                for (Card card : cards) {
                    if (!Zone.EXILED.equals(game.getState().getZone(card.getId()))) {
                        cards.remove(card);
                    }
                }
                if (!cards.isEmpty()) {
                    ContinuousEffect effect = new ActOnImpulseMayPlayExiledEffect();
                    effect.setTargetPointer(new FixedTargets(cards, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }

}

class ActOnImpulseMayPlayExiledEffect extends AsThoughEffectImpl {

    public ActOnImpulseMayPlayExiledEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
    }

    public ActOnImpulseMayPlayExiledEffect(final ActOnImpulseMayPlayExiledEffect effect) {
        super(effect);
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
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return affectedControllerId.equals(source.getControllerId())
                && getTargetPointer().getTargets(game, source).contains(objectId);
    }

}
