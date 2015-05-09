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
package mage.sets.judgment;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
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
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class Spelljack extends CardImpl {

    public Spelljack(UUID ownerId) {
        super(ownerId, 51, "Spelljack", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{U}{U}{U}");
        this.expansionSetCode = "JUD";

        // Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard. You may play it without paying its mana cost for as long as it remains exiled.
        this.getSpellAbility().addEffect(new SpelljackEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public Spelljack(final Spelljack card) {
        super(card);
    }

    @Override
    public Spelljack copy() {
        return new Spelljack(this);
    }
}

class SpelljackEffect extends OneShotEffect {
    
    SpelljackEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard. You may play it without paying its mana cost for as long as it remains exiled";
    }
    
    SpelljackEffect(final SpelljackEffect effect) {
        super(effect);
    }
    
    @Override
    public SpelljackEffect copy() {
        return new SpelljackEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        UUID objectId = targetPointer.getFirst(game, source);
        UUID sourceId = source.getSourceId();

        StackObject stackObject = game.getStack().getStackObject(objectId);
        if (stackObject != null && !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER, objectId, sourceId, stackObject.getControllerId()))) {
            game.rememberLKI(objectId, Zone.STACK, stackObject);
            game.getStack().remove(stackObject);
            if (!((Spell) stackObject).isCopiedSpell()) {
                MageObject card = game.getObject(stackObject.getSourceId());
                if (card instanceof Card) {
                    ((Card) card).moveToZone(Zone.EXILED, sourceId, game, true);
                    ContinuousEffect effect = new SpelljackCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTERED, objectId, sourceId, stackObject.getControllerId()));
            return true;
        }
        return false;
    }
}

class SpelljackCastFromExileEffect extends AsThoughEffectImpl {

    SpelljackCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may cast that card without paying its mana cost as long as it remains exiled";
    }

    SpelljackCastFromExileEffect(final SpelljackCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SpelljackCastFromExileEffect copy() {
        return new SpelljackCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            if (getTargetPointer().getFirst(game, source) == null) {
                this.discard();
                return false;
            }
            if (sourceId.equals(getTargetPointer().getFirst(game, source))) {
                Card card = game.getCard(sourceId);
                if (card != null) {
                    if (game.getState().getZone(sourceId) == Zone.EXILED) {
                        Player player = game.getPlayer(affectedControllerId);
                        player.setCastSourceIdWithAlternateMana(sourceId, null);
                        return true;
                    }
                    else {
                        this.discard();
                    }
                }
            }
        }
        return false;
    }
}