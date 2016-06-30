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
package mage.sets.coldsnap;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.SplitCard;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class JestersScepter extends CardImpl {

    public JestersScepter(UUID ownerId) {
        super(ownerId, 137, "Jester's Scepter", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "CSP";

        // When Jester's Scepter enters the battlefield, exile the top five cards of target player's library face down.
        Ability ability = new EntersBattlefieldTriggeredAbility(new JestersScepterEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // You may look at those cards for as long as they remain exiled.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new JestersScepterLookAtCardEffect()));

        // {2}, {tap}, Put a card exiled with Jester's Scepter into its owner's graveyard: Counter target spell if it has the same name as that card.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JestersScepterCounterEffect(), new ManaCostsImpl("{2}"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new JestersScepterCost());
        ability2.addTarget(new TargetSpell());
        this.addAbility(ability2);

    }

    public JestersScepter(final JestersScepter card) {
        super(card);
    }

    @Override
    public JestersScepter copy() {
        return new JestersScepter(this);
    }
}

class JestersScepterEffect extends OneShotEffect {

    public JestersScepterEffect() {
        super(Outcome.DrawCard);
        staticText = "exile the top five cards of target player's library face down";
    }

    public JestersScepterEffect(final JestersScepterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetedPlayer = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null
                && targetedPlayer != null
                && sourceObject != null) {
            if (targetedPlayer.getLibrary().size() > 0) {
                Set<Card> cardsToExile = targetedPlayer.getLibrary().getTopCards(game, 5);
                for (Card card : cardsToExile) {
                    if (card.moveToExile(CardUtil.getCardExileZoneId(game, source), new StringBuilder(sourceObject.getName()).toString(), source.getSourceId(), game)) {
                        card.setFaceDown(true, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public JestersScepterEffect copy() {
        return new JestersScepterEffect(this);
    }
}

class JestersScepterLookAtCardEffect extends AsThoughEffectImpl {

    public JestersScepterLookAtCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    public JestersScepterLookAtCardEffect(final JestersScepterLookAtCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public JestersScepterLookAtCardEffect copy() {
        return new JestersScepterLookAtCardEffect(this);
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
                return exile != null
                        && exile.contains(objectId);
            }
        }
        return false;
    }
}

class JestersScepterCost extends CostImpl {

    public JestersScepterCost() {
        this.text = "Put a card exiled with {this} into its owner's graveyard";
    }

    public JestersScepterCost(JestersScepterCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            TargetCardInExile target = new TargetCardInExile(new FilterCard(), CardUtil.getCardExileZoneId(game, ability));
            target.setNotTarget(true);
            Cards cards = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, ability));
            if (cards.size() > 0
                    && controller.choose(Outcome.Benefit, cards, target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    if (controller.moveCardToGraveyardWithInfo(card, sourceId, game, Zone.EXILED)) {
                        // Split Card check
                        if (card instanceof SplitCard) {
                            game.getState().setValue(sourceId + "_nameOfExiledCardPayment", ((SplitCard) card).getLeftHalfCard().getName());
                            game.getState().setValue(sourceId + "_nameOfExiledCardPayment2", ((SplitCard) card).getRightHalfCard().getName());
                            paid = true;
                            return paid;
                        }
                        game.getState().setValue(sourceId + "_nameOfExiledCardPayment", card.getName());
                        paid = true;
                    }
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null;
    }

    @Override
    public JestersScepterCost copy() {
        return new JestersScepterCost(this);
    }
}

class JestersScepterCounterEffect extends OneShotEffect {

    JestersScepterCounterEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell if it has the same name as that card";
    }

    JestersScepterCounterEffect(final JestersScepterCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            // In case of Split Card
            String nameOfExiledCardPayment = (String) game.getState().getValue(source.getSourceId() + "_nameOfExiledCardPayment");
            String nameOfExiledCardPayment2 = (String) game.getState().getValue(source.getSourceId() + "_nameOfExiledCardPayment2");
            if (nameOfExiledCardPayment != null) {
                if (nameOfExiledCardPayment.equals(spell.getCard().getName())
                        || (nameOfExiledCardPayment2 != null) && nameOfExiledCardPayment2.equals(spell.getCard().getName())) {
                    return game.getStack().counter(targetPointer.getFirst(game, source), source.getSourceId(), game);
                }
            }
        }
        return false;
    }

    @Override
    public JestersScepterCounterEffect copy() {
        return new JestersScepterCounterEffect(this);
    }
}
