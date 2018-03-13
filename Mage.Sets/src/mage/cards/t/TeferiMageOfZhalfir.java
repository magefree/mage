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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class TeferiMageOfZhalfir extends CardImpl {

    public TeferiMageOfZhalfir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Creature cards you own that aren't on the battlefield have flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TeferiMageOfZhalfirAddFlashEffect()));

        // Each opponent can cast spells only any time he or she could cast a sorcery.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TeferiMageOfZhalfirReplacementEffect()));

    }

    public TeferiMageOfZhalfir(final TeferiMageOfZhalfir card) {
        super(card);
    }

    @Override
    public TeferiMageOfZhalfir copy() {
        return new TeferiMageOfZhalfir(this);
    }
}

class TeferiMageOfZhalfirAddFlashEffect extends ContinuousEffectImpl {

    public TeferiMageOfZhalfirAddFlashEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Creature cards you own that aren't on the battlefield have flash";
    }

    public TeferiMageOfZhalfirAddFlashEffect(final TeferiMageOfZhalfirAddFlashEffect effect) {
        super(effect);
    }

    @Override
    public TeferiMageOfZhalfirAddFlashEffect copy() {
        return new TeferiMageOfZhalfirAddFlashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // in graveyard
            for (UUID cardId : controller.getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card.isCreature()) {
                    game.getState().addOtherAbility(card, FlashAbility.getInstance());
                }
            }
            // on Hand
            for (UUID cardId : controller.getHand()) {
                Card card = game.getCard(cardId);
                if (card.isCreature()) {
                    game.getState().addOtherAbility(card, FlashAbility.getInstance());
                }
            }
            // in Exile
            for (Card card : game.getState().getExile().getAllCards(game)) {
                if (card.getOwnerId().equals(controller.getId()) && card.isCreature()) {
                    game.getState().addOtherAbility(card, FlashAbility.getInstance());
                }
            }
            // in Library (e.g. for Mystical Teachings)
            for (Card card : controller.getLibrary().getCards(game)) {
                if (card.getOwnerId().equals(controller.getId()) && card.isCreature()) {
                    game.getState().addOtherAbility(card, FlashAbility.getInstance());
                }
            }
            // commander in command zone
            for (UUID commanderId : controller.getCommandersIds()) {
                if (game.getState().getZone(commanderId) == Zone.COMMAND) {
                    Card card = game.getCard(commanderId);
                    if (card.isCreature()) {
                        game.getState().addOtherAbility(card, FlashAbility.getInstance());
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class TeferiMageOfZhalfirReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    TeferiMageOfZhalfirReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each opponent can cast spells only any time he or she could cast a sorcery";
    }

    TeferiMageOfZhalfirReplacementEffect(final TeferiMageOfZhalfirReplacementEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can cast spells only any time you could cast a sorcery  (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
            return !game.canPlaySorcery(event.getPlayerId());
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TeferiMageOfZhalfirReplacementEffect copy() {
        return new TeferiMageOfZhalfirReplacementEffect(this);
    }
}
