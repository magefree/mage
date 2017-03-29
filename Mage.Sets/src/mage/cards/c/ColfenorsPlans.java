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
package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.effects.common.continuous.CantCastMoreThanOneSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class ColfenorsPlans extends CardImpl {

    public ColfenorsPlans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // When Colfenor's Plans enters the battlefield, exile the top seven cards of your library face down.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ColfenorsPlansExileEffect(), false));

        // You may look at and play cards exiled with Colfenor's Plans.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ColfenorsPlansPlayCardEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ColfenorsPlansLookAtCardEffect()));

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));

        // You can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantCastMoreThanOneSpellEffect(TargetController.YOU)));

    }

    public ColfenorsPlans(final ColfenorsPlans card) {
        super(card);
    }

    @Override
    public ColfenorsPlans copy() {
        return new ColfenorsPlans(this);
    }
}

class ColfenorsPlansExileEffect extends OneShotEffect {

    public ColfenorsPlansExileEffect() {
        super(Outcome.DrawCard);
        staticText = "exile the top seven cards of your library face down";
    }

    public ColfenorsPlansExileEffect(final ColfenorsPlansExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null && sourceObject != null) {
            for (int i = 0; i < 7; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    if (player.moveCardToExileWithInfo(card, CardUtil.getCardExileZoneId(game, source), sourceObject.getIdName(), source.getSourceId(), game, Zone.LIBRARY, true)) {
                        card.setFaceDown(true, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ColfenorsPlansExileEffect copy() {
        return new ColfenorsPlansExileEffect(this);
    }
}

class ColfenorsPlansPlayCardEffect extends AsThoughEffectImpl {

    public ColfenorsPlansPlayCardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may play cards exiled with {this}";
    }

    public ColfenorsPlansPlayCardEffect(final ColfenorsPlansPlayCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ColfenorsPlansPlayCardEffect copy() {
        return new ColfenorsPlansPlayCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(objectId);
        if (affectedControllerId.equals(source.getControllerId()) && card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
            ExileZone zone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            return zone != null && zone.contains(card.getId());
        }
        return false;
    }
}

class ColfenorsPlansLookAtCardEffect extends AsThoughEffectImpl {

    public ColfenorsPlansLookAtCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    public ColfenorsPlansLookAtCardEffect(final ColfenorsPlansLookAtCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ColfenorsPlansLookAtCardEffect copy() {
        return new ColfenorsPlansLookAtCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                MageObject sourceObject = game.getObject(source.getSourceId());
                if (sourceObject == null) {
                    return false;
                }
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                ExileZone exile = game.getExile().getExileZone(exileId);
                return exile != null && exile.contains(objectId);
            }
        }
        return false;
    }

}
