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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;
import mage.util.GameLog;

/**
 *
 * @author LevelX2
 */
public class AlhammarretHighArbiter extends CardImpl {

    public AlhammarretHighArbiter(UUID ownerId) {
        super(ownerId, 43, "Alhammarret, High Arbiter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.expansionSetCode = "ORI";
        this.supertype.add("Legendary");
        this.subtype.add("Sphinx");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // As Alhammarret, High Arbiter enters the battlefield, each opponent reveals his or her hand. You choose the name of a nonland card revealed this way.
        // Your opponents can't cast spells with the chosen name.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EntersBattlefieldEffect(new AlhammarretHighArbiterEffect(), "")));
    }

    public AlhammarretHighArbiter(final AlhammarretHighArbiter card) {
        super(card);
    }

    @Override
    public AlhammarretHighArbiter copy() {
        return new AlhammarretHighArbiter(this);
    }
}

class AlhammarretHighArbiterEffect extends OneShotEffect {

    public AlhammarretHighArbiterEffect() {
        super(Outcome.Benefit);
        this.staticText = "As {this} enters the battlefield, each opponent reveals his or her hand. You choose the name of a nonland card revealed this way."
                + "<br>Your opponents can't cast spells with the chosen name";
    }

    public AlhammarretHighArbiterEffect(final AlhammarretHighArbiterEffect effect) {
        super(effect);
    }

    @Override
    public AlhammarretHighArbiterEffect copy() {
        return new AlhammarretHighArbiterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards revealedCards = new CardsImpl(Zone.PICK);
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (playerId != controller.getId()) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        Cards cards = new CardsImpl(opponent.getHand());
                        opponent.revealCards(opponent.getName() + "'s hand", cards, game);
                        revealedCards.addAll(cards);
                    }
                }
            }
            TargetCard target = new TargetCard(Zone.HAND, new FilterNonlandCard("nonland card from an opponents hand"));
            controller.chooseTarget(Outcome.Benefit, revealedCards, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                game.informPlayers("The choosen card name is [" + GameLog.getColoredObjectName(card) + "]");
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null) {
                    sourcePermanent.addInfo("chosen card name", CardUtil.addToolTipMarkTags("Chosen card name: " + card.getName()), game);
                }
                game.addEffect(new AlhammarretHighArbiterCantCastEffect(card.getName()), source);
            }
            return true;

        }
        return false;
    }
}

class AlhammarretHighArbiterCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    String cardName;

    public AlhammarretHighArbiterCantCastEffect(String cardName) {
        super(Duration.Custom, Outcome.Benefit);
        this.cardName = cardName;
        staticText = "Your opponents can't cast spells with the chosen name";
    }

    public AlhammarretHighArbiterCantCastEffect(final AlhammarretHighArbiterCantCastEffect effect) {
        super(effect);
        this.cardName = effect.cardName;
    }

    @Override
    public AlhammarretHighArbiterCantCastEffect copy() {
        return new AlhammarretHighArbiterCantCastEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        return sourceObject == null || sourceObject.getZoneChangeCounter(game) != source.getSourceObjectZoneChangeCounter();
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You may not cast a card named " + cardName + " (" + mageObject.getLogName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null && object.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }
}
