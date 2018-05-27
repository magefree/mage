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
package mage.cards.s;

import java.util.Objects;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public class StolenStrategy extends CardImpl {

    public StolenStrategy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        // At the beginning of your upkeep, exile the top card of each opponent's library. Until end of turn, you may cast nonland cards from among those exiled cards, and you may spend mana as though it were mana of any color to cast those spells.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new StolenStrategyEffect(), TargetController.YOU, false));
    }

    public StolenStrategy(final StolenStrategy card) {
        super(card);
    }

    @Override
    public StolenStrategy copy() {
        return new StolenStrategy(this);
    }
}

class StolenStrategyEffect extends OneShotEffect {

    public StolenStrategyEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "exile the top card of each opponent's library. "
                + "Until end of turn, you may cast nonland cards from among those exiled cards, "
                + "and you may spend mana as though it were mana of any color to cast those spells";
    }

    public StolenStrategyEffect(final StolenStrategyEffect effect) {
        super(effect);
    }

    @Override
    public StolenStrategyEffect copy() {
        return new StolenStrategyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            if (!controller.hasOpponent(playerId, game)) {
                continue;
            }
            Player damagedPlayer = game.getPlayer(playerId);
            if (damagedPlayer == null) {
                continue;
            }
            MageObject sourceObject = game.getObject(source.getSourceId());
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            Card card = damagedPlayer.getLibrary().getFromTop(game);
            if (card != null) {
                // move card to exile
                controller.moveCardToExileWithInfo(card, exileId, sourceObject.getIdName(), source.getSourceId(), game, Zone.LIBRARY, true);
                // Add effects only if the card has a spellAbility (e.g. not for lands).
                if (!card.isLand() && card.getSpellAbility() != null) {
                    // allow to cast the card
                    game.addEffect(new StolenStrategyCastFromExileEffect(card.getId(), exileId), source);
                    // and you may spend mana as though it were mana of any color to cast it
                    ContinuousEffect effect = new StolenStrategySpendAnyManaEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
        }
        return true;
    }
}

class StolenStrategyCastFromExileEffect extends AsThoughEffectImpl {

    private UUID cardId;
    private UUID exileId;

    public StolenStrategyCastFromExileEffect(UUID cardId, UUID exileId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, you may cast that card and you may spend mana as though it were mana of any color to cast it";
        this.cardId = cardId;
        this.exileId = exileId;
    }

    public StolenStrategyCastFromExileEffect(final StolenStrategyCastFromExileEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public StolenStrategyCastFromExileEffect copy() {
        return new StolenStrategyCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(cardId) && source.getControllerId().equals(affectedControllerId)) {
            ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
            return exileZone != null && exileZone.contains(cardId);
        }
        return false;
    }
}

class StolenStrategySpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public StolenStrategySpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast it";
    }

    public StolenStrategySpendAnyManaEffect(final StolenStrategySpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public StolenStrategySpendAnyManaEffect copy() {
        return new StolenStrategySpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.getControllerId().equals(affectedControllerId)
                && Objects.equals(objectId, ((FixedTarget) getTargetPointer()).getTarget())
                && ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(objectId)
                && (((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(objectId))
                && game.getState().getZone(objectId) == Zone.STACK;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }

}
