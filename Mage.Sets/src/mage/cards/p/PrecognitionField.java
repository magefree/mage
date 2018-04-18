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
package mage.cards.p;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;

/**
 * @author rscoates
 */
public class PrecognitionField extends CardImpl {

    public PrecognitionField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");


        // You may look at the top card of your library.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PrecognitionFieldTopCardRevealedEffect()));
        // You may cast the top card of your library if it's an instant or sorcery card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PrecognitionFieldTopCardCastEffect()));
        // {3}: Exile the top card of your library.
        Effect effect = new PrecognitionFieldExileEffect();
        effect.setText("Exile the top card of your library.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(3));
    }

    public PrecognitionField(final PrecognitionField card) {
        super(card);
    }

    @Override
    public PrecognitionField copy() {
        return new PrecognitionField(this);
    }
}

class PrecognitionFieldTopCardRevealedEffect extends ContinuousEffectImpl {

    public PrecognitionFieldTopCardRevealedEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may look at the top card of your library. (You may do this at any time.)";
    }

    public PrecognitionFieldTopCardRevealedEffect(final PrecognitionFieldTopCardRevealedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card topCard = controller.getLibrary().getFromTop(game);
            if (topCard != null) {
                MageObject precognitionField = source.getSourceObject(game);
                if (precognitionField != null) {
                    controller.lookAtCards("Top card of " + precognitionField.getIdName() + " controller's library", topCard, game);
                }
            }
        }
        return true;
    }

    @Override
    public PrecognitionFieldTopCardRevealedEffect copy() {
        return new PrecognitionFieldTopCardRevealedEffect(this);
    }
}

class PrecognitionFieldTopCardCastEffect extends AsThoughEffectImpl {

    public PrecognitionFieldTopCardCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast the top card of your library if it’s an instant or sorcery card.";
    }

    public PrecognitionFieldTopCardCastEffect(final PrecognitionFieldTopCardCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PrecognitionFieldTopCardCastEffect copy() {
        return new PrecognitionFieldTopCardCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                Player controller = game.getPlayer(affectedControllerId);
                if (controller != null) {
                    Card topCard = controller.getLibrary().getFromTop(game);
                    MageObject precognitionField = game.getObject(source.getSourceId());
                    if (precognitionField != null
                            && topCard != null) {
                        return topCard == card
                                && (topCard.isInstant() || topCard.isSorcery())
                                && topCard.getSpellAbility() != null
                                && topCard.getSpellAbility().spellCanBeActivatedRegularlyNow(controller.getId(), game);
                    }
                }
            }
        }
        return false;
    }
}

class PrecognitionFieldExileEffect extends OneShotEffect {

    public PrecognitionFieldExileEffect() {
        super(Outcome.Benefit);
    }

    public PrecognitionFieldExileEffect(final PrecognitionFieldExileEffect effect) {
        super(effect);
    }

    @Override
    public PrecognitionFieldExileEffect copy() {
        return new PrecognitionFieldExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null && controller.getLibrary().hasCards()) {
            Library library = controller.getLibrary();
            Card card = library.removeFromTop(game);
            if (card != null) {
                card.moveToExile(id, "Precognition Field Exile", source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }
}
