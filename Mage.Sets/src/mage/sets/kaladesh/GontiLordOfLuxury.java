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
package mage.sets.kaladesh;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class GontiLordOfLuxury extends CardImpl {

    protected static final String VALUE_PREFIX = "ExileZones";

    public GontiLordOfLuxury(UUID ownerId) {
        super(ownerId, 84, "Gonti, Lord of Luxury", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "KLD";
        this.supertype.add("Legendary");
        this.subtype.add("Aetherborn");
        this.subtype.add("Rogue");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Gonti, Lord of Luxury enters the battlefield, look at the top four cards of target opponent's library, exile one of them face down, then put the rest on the bottom of that library in a random order. For as long as that card remains exiled, you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GontiLordOfLuxuryEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    public GontiLordOfLuxury(final GontiLordOfLuxury card) {
        super(card);
    }

    @Override
    public GontiLordOfLuxury copy() {
        return new GontiLordOfLuxury(this);
    }
}

class GontiLordOfLuxuryEffect extends OneShotEffect {

    public GontiLordOfLuxuryEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top four cards of target opponent's library, exile one of them face down, then put the rest on the bottom of that library in a random order. For as long as that card remains exiled, you may look at it, you may cast it, and you may spend mana as though it were mana of any type to cast it";
    }

    public GontiLordOfLuxuryEffect(final GontiLordOfLuxuryEffect effect) {
        super(effect);
    }

    @Override
    public GontiLordOfLuxuryEffect copy() {
        return new GontiLordOfLuxuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && opponent != null && sourceObject != null) {
            Cards topCards = new CardsImpl();
            topCards.addAll(opponent.getLibrary().getTopCards(game, 4));
            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to exile"));
            if (controller.choose(outcome, topCards, target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    topCards.remove(card);
                    // move card to exile
                    UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                    card.setFaceDown(true, game);
                    controller.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName());
                    card.setFaceDown(true, game);
                    Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(GontiLordOfLuxury.VALUE_PREFIX + source.getSourceId().toString());
                    if (exileZones == null) {
                        exileZones = new HashSet<>();
                        game.getState().setValue(GontiLordOfLuxury.VALUE_PREFIX + source.getSourceId().toString(), exileZones);
                    }
                    exileZones.add(exileZoneId);
                    // allow to cast the card
                    ContinuousEffect effect = new GontiLordOfLuxuryCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                    // and you may spend mana as though it were mana of any color to cast it
                    effect = new GontiLordOfLuxurySpendAnyManaEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
                while (!topCards.isEmpty()) {
                    Card libCard = topCards.getRandom(game);
                    topCards.remove(card);
                    controller.moveCardToLibraryWithInfo(libCard, source.getSourceId(), game, Zone.LIBRARY, false, false);
                }
            }
            return true;
        }
        return false;
    }
}

class GontiLordOfLuxuryCastFromExileEffect extends AsThoughEffectImpl {

    public GontiLordOfLuxuryCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell";
    }

    public GontiLordOfLuxuryCastFromExileEffect(final GontiLordOfLuxuryCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GontiLordOfLuxuryCastFromExileEffect copy() {
        return new GontiLordOfLuxuryCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(getTargetPointer().getFirst(game, source))) {
            if (affectedControllerId.equals(source.getControllerId())) {
                return true;
            }
        } else if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
            // object has moved zone so effect can be discarted
            this.discard();
        }
        return false;
    }
}

class GontiLordOfLuxurySpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public GontiLordOfLuxurySpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast it";
    }

    public GontiLordOfLuxurySpendAnyManaEffect(final GontiLordOfLuxurySpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GontiLordOfLuxurySpendAnyManaEffect copy() {
        return new GontiLordOfLuxurySpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(((FixedTarget) getTargetPointer()).getTarget())
                && game.getState().getZoneChangeCounter(objectId) <= ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {

            if (affectedControllerId.equals(source.getControllerId())) {
                // if the card moved from exile to spell the zone change counter is increased by 1
                if (game.getState().getZoneChangeCounter(objectId) == ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {
                    return true;
                }
            }

        } else if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
            // object has moved zone so effect can be discarted
            this.discard();
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

class GontiLordOfLuxuryLookEffect extends AsThoughEffectImpl {

    public GontiLordOfLuxuryLookEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at the cards exiled with {this}";
    }

    public GontiLordOfLuxuryLookEffect(final GontiLordOfLuxuryLookEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GontiLordOfLuxuryLookEffect copy() {
        return new GontiLordOfLuxuryLookEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId()) && game.getState().getZone(objectId).equals(Zone.EXILED)) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = source.getSourceObject(game);
            if (controller != null && sourceObject != null) {
                Card card = game.getCard(objectId);
                if (card != null && card.isFaceDown(game)) {
                    Set<UUID> exileZones = (Set<UUID>) game.getState().getValue(GontiLordOfLuxury.VALUE_PREFIX + source.getSourceId().toString());
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
