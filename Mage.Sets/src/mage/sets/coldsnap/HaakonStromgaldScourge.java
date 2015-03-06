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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;

/**
 *
 * @author Mainiack11
 */
public class HaakonStromgaldScourge extends CardImpl {

    public HaakonStromgaldScourge(UUID ownerId) {
        super(ownerId, 61, "Haakon, Stromgald Scourge", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "CSP";
        this.supertype.add("Legendary");
        this.subtype.add("Zombie");
        this.subtype.add("Knight");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You may cast Haakon, Stromgald Scourge from your graveyard, but not from anywhere else.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new HaakonStromgaldScourgePlayEffect());
        ability.addEffect(new HaakonStromgaldScourgePlayEffect2());
        this.addAbility(ability);

        // As long as Haakon is on the battlefield, you may play Knight cards from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HaakonPlayKnightsFromGraveyardEffect()));

        // When Haakon dies, you lose 2 life.
        this.addAbility(new DiesTriggeredAbility(new LoseLifeSourceControllerEffect(2)));

    }

    public HaakonStromgaldScourge(final HaakonStromgaldScourge card) {
        super(card);
    }

    @Override
    public HaakonStromgaldScourge copy() {
        return new HaakonStromgaldScourge(this);
    }

}

class HaakonStromgaldScourgePlayEffect extends AsThoughEffectImpl {

    public HaakonStromgaldScourgePlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard";
    }

    public HaakonStromgaldScourgePlayEffect(final HaakonStromgaldScourgePlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HaakonStromgaldScourgePlayEffect copy() {
        return new HaakonStromgaldScourgePlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(source.getSourceId()) &&
                affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }
}

class HaakonStromgaldScourgePlayEffect2 extends ContinuousRuleModifyingEffectImpl {

    public HaakonStromgaldScourgePlayEffect2() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = ", but not from anywhere else";
    }

    public HaakonStromgaldScourgePlayEffect2 (final HaakonStromgaldScourgePlayEffect2 effect) {
        super(effect);
    }

    @Override
    public HaakonStromgaldScourgePlayEffect2 copy() {
        return new HaakonStromgaldScourgePlayEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if(event.getType() == GameEvent.EventType.CAST_SPELL) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && card.getId().equals(source.getSourceId())) {
                Zone zone = game.getState().getZone(card.getId());
                if (zone != null && (zone != Zone.GRAVEYARD)) {
                    return true;
                }
            }
        }
        return false;
    }
}

class HaakonPlayKnightsFromGraveyardEffect extends AsThoughEffectImpl {

    public HaakonPlayKnightsFromGraveyardEffect () {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as {this} is on the battlefield, you may play Knight cards from your graveyard";
    }

    public HaakonPlayKnightsFromGraveyardEffect(final HaakonPlayKnightsFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HaakonPlayKnightsFromGraveyardEffect copy() {
        return new HaakonPlayKnightsFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {       
        if (affectedControllerId.equals(source.getControllerId())) {
            Card knightToCast = game.getCard(objectId);
            if (knightToCast != null
                    && knightToCast.hasSubtype("Knight")
                    && knightToCast.getOwnerId().equals(source.getControllerId())
                    && game.getState().getZone(objectId) == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }
}

