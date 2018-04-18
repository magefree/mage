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
package mage.cards.m;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class MuldrothaTheGravetide extends CardImpl {

    public MuldrothaTheGravetide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // During each of your turns, you may play up to one permanent card of each permanent type from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MuldrothaTheGravetideCastFromGraveyardEffect()), new MuldrothaTheGravetideWatcher());
    }

    public MuldrothaTheGravetide(final MuldrothaTheGravetide card) {
        super(card);
    }

    @Override
    public MuldrothaTheGravetide copy() {
        return new MuldrothaTheGravetide(this);
    }
}

class MuldrothaTheGravetideCastFromGraveyardEffect extends AsThoughEffectImpl {

    public MuldrothaTheGravetideCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "During each of your turns, you may play up to one permanent card of each permanent type from your graveyard. "
                + "<i>(If a card has multiple permanent types, choose one as you play it.)</i>";
    }

    public MuldrothaTheGravetideCastFromGraveyardEffect(final MuldrothaTheGravetideCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MuldrothaTheGravetideCastFromGraveyardEffect copy() {
        return new MuldrothaTheGravetideCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.getControllerId().equals(affectedControllerId) && Zone.GRAVEYARD.equals(game.getState().getZone(objectId))) {
            MuldrothaTheGravetideWatcher watcher = (MuldrothaTheGravetideWatcher) game.getState().getWatchers().get(MuldrothaTheGravetideWatcher.class.getSimpleName());
            MageObject mageObject = game.getObject(objectId);
            if (mageObject != null && watcher != null) {
                for (CardType cardType : mageObject.getCardType()) {
                    if (cardType.isPermanentType()) {
                        if (!watcher.permanentTypePlayedFromGraveyard(affectedControllerId, cardType)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

class MuldrothaTheGravetideWatcher extends Watcher {

    final HashMap<UUID, Set<CardType>> playerPlayedPermanentTypes = new HashMap<>(); // player that played permanent types from graveyard
    private Zone fromZone;

    public MuldrothaTheGravetideWatcher() {
        super(MuldrothaTheGravetideWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public MuldrothaTheGravetideWatcher(final MuldrothaTheGravetideWatcher watcher) {
        super(watcher);
        playerPlayedPermanentTypes.putAll(watcher.playerPlayedPermanentTypes);
    }

    @Override
    public MuldrothaTheGravetideWatcher copy() {
        return new MuldrothaTheGravetideWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.PLAY_LAND) {
            fromZone = game.getState().getZone(event.getTargetId()); // Remember the Zone the land came from
        }
        if (event.getType() == GameEvent.EventType.LAND_PLAYED && fromZone.equals(Zone.GRAVEYARD)) {
            addPermanentTypes(game.getPermanentOrLKIBattlefield(event.getTargetId()), game);
        }

        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell.getFromZone().equals(Zone.GRAVEYARD)) {
                addPermanentTypes(spell, game);
            }
        }
    }

    private void addPermanentTypes(Card mageObject, Game game) {
        if (mageObject != null) {
            UUID playerId = null;
            if (mageObject instanceof Spell) {
                playerId = ((Spell) mageObject).getControllerId();
            } else if (mageObject instanceof Permanent) {
                playerId = ((Permanent) mageObject).getControllerId();
            }
            if (playerId != null) {
                Set<CardType> permanentTypes = playerPlayedPermanentTypes.get(playerId);
                if (permanentTypes == null) {
                    permanentTypes = EnumSet.noneOf(CardType.class);
                    playerPlayedPermanentTypes.put(playerId, permanentTypes);
                }
                Set<CardType> typesNotCast = EnumSet.noneOf(CardType.class);
                for (CardType cardType : mageObject.getCardType()) {
                    if (cardType.isPermanentType()) {
                        if (!permanentTypes.contains(cardType)) {
                            typesNotCast.add(cardType);
                        }
                    }
                }
                if (typesNotCast.size() <= 1) {
                    permanentTypes.addAll(typesNotCast);
                } else {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        Choice typeChoice = new ChoiceImpl(true);
                        typeChoice.setMessage("Choose permanent type you consume for casting from graveyard.");
                        for (CardType cardType : typesNotCast) {
                            typeChoice.getChoices().add(cardType.toString());
                        }
                        if (player.choose(Outcome.Detriment, typeChoice, game)) {
                            String typeName = typeChoice.getChoice();
                            CardType chosenType = null;
                            for (CardType cardType : CardType.values()) {
                                if (cardType.toString().equals(typeName)) {
                                    chosenType = cardType;
                                    break;
                                }
                            }
                            if (chosenType != null) {
                                permanentTypes.add(chosenType);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        playerPlayedPermanentTypes.clear();
        super.reset();
    }

    public boolean permanentTypePlayedFromGraveyard(UUID playerId, CardType cardType) {
        Set<CardType> permanentTypes = playerPlayedPermanentTypes.get(playerId);
        if (permanentTypes != null) {
            return permanentTypes.contains(cardType);
        }
        return false;
    }

}
