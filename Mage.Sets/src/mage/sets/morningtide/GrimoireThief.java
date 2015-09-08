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
package mage.sets.morningtide;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class GrimoireThief extends CardImpl {
    
    protected static final String VALUE_PREFIX = "ExileZones";
    
    public GrimoireThief(UUID ownerId) {
        super(ownerId, 35, "Grimoire Thief", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{U}{U}");
        this.expansionSetCode = "MOR";
        this.subtype.add("Merfolk");
        this.subtype.add("Rogue");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Grimoire Thief becomes tapped, exile the top three cards of target opponent's library face down.
        Ability ability = new BecomesTappedTriggeredAbility(new GrimoireThiefExileEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // You may look at cards exiled with Grimoire Thief.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new GrimoireThiefLookEffect()));

        // {U}, Sacrifice Grimoire Thief: Turn all cards exiled with Grimoire Thief face up. Counter all spells with those names.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrimoireThiefCounterspellEffect(), new ManaCostsImpl("{U}"));
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);
        
    }
    
    public GrimoireThief(final GrimoireThief card) {
        super(card);
    }
    
    @Override
    public GrimoireThief copy() {
        return new GrimoireThief(this);
    }
}

class GrimoireThiefExileEffect extends OneShotEffect {
    
    public GrimoireThiefExileEffect() {
        super(Outcome.Discard);
        staticText = "exile the top three cards of target opponent's library face down";
    }
    
    public GrimoireThiefExileEffect(final GrimoireThiefExileEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        if (targetOpponent != null) {
            Set<Card> cards = targetOpponent.getLibrary().getTopCards(game, 3);
            MageObject sourceObject = source.getSourceObject(game);
            if (!cards.isEmpty() && sourceObject != null) {
                for (Card card : cards) {
                    card.setFaceDown(true, game);
                }
                UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                targetOpponent.moveCardsToExile(cards, source, game, false, exileZoneId, sourceObject.getIdName());
                for (Card card : cards) {
                    card.setFaceDown(true, game);
                }
                Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(GrimoireThief.VALUE_PREFIX + source.getSourceId().toString());
                if (exileZones == null) {
                    exileZones = new HashSet<>();
                    game.getState().setValue(GrimoireThief.VALUE_PREFIX + source.getSourceId().toString(), exileZones);
                }
                exileZones.add(exileZoneId);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public GrimoireThiefExileEffect copy() {
        return new GrimoireThiefExileEffect(this);
    }
}

class GrimoireThiefLookEffect extends AsThoughEffectImpl {
    
    public GrimoireThiefLookEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at the cards exiled with {this}";
    }
    
    public GrimoireThiefLookEffect(final GrimoireThiefLookEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
    
    @Override
    public GrimoireThiefLookEffect copy() {
        return new GrimoireThiefLookEffect(this);
    }
    
    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId()) && game.getState().getZone(objectId).equals(Zone.EXILED)) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = source.getSourceObject(game);
            if (controller != null && sourceObject != null) {
                Card card = game.getCard(objectId);
                if (card != null && card.isFaceDown(game)) {
                    Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(GrimoireThief.VALUE_PREFIX + source.getSourceId().toString());
                    if (exileZones != null) {
                        for (ExileZone exileZone : game.getExile().getExileZones()) {
                            if (exileZone.contains(objectId)) {
                                if (!exileZones.contains(exileZone.getId())) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class GrimoireThiefCounterspellEffect extends OneShotEffect {
    
    public GrimoireThiefCounterspellEffect() {
        super(Outcome.Discard);
        staticText = "Turn all cards exiled with {this} face up. Counter all spells with those names";
    }
    
    public GrimoireThiefCounterspellEffect(final GrimoireThiefCounterspellEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = new CardsImpl();
        MageObject sourceObject = game.getObject(source.getSourceId());
        Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(GrimoireThief.VALUE_PREFIX + source.getSourceId().toString());
        if (exileZones != null) {
            for (ExileZone exileZone : game.getExile().getExileZones()) {
                if (!exileZone.isEmpty()) {
                    cards.addAll(exileZone.getCards(game));
                }
            }
            // set face up first
            for (Card card : cards.getCards(game)) {
                card.setFaceDown(false, game);
            }
            // then counter any with the same name as the card exiled with Grimoire Thief
            for (Card card : cards.getCards(game)) {
                for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext();) {
                    StackObject stackObject = iterator.next();
                    MageObject mageObject = game.getObject(card.getId());
                    // handle split cards
                    if (mageObject instanceof SplitCard) {
                        if (stackObject instanceof Spell
                                && (stackObject.getName().contains(((SplitCard)mageObject).getLeftHalfCard().getName())
                                || stackObject.getName().contains(((SplitCard)mageObject).getRightHalfCard().getName()))) {
                            Spell spell = (Spell) stackObject;
                            spell.counter(source.getSourceId(), game);
                            game.informPlayers(sourceObject.getLogName() + ": the split-card spell named " + spell.getName() + " was countered.");
                        }
                    }
                    if (stackObject instanceof Spell
                            && stackObject.getName().contains(card.getName())) {
                        Spell spell = (Spell) stackObject;
                        spell.counter(source.getSourceId(), game);
                        game.informPlayers(sourceObject.getLogName() + ": the spell named " + spell.getName() + " was countered.");
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public GrimoireThiefCounterspellEffect copy() {
        return new GrimoireThiefCounterspellEffect(this);
    }
}
