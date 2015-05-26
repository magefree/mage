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
package mage.sets.invasion;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.CantCastMoreThanOneSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class YawgmothsAgenda extends CardImpl {

    public YawgmothsAgenda(UUID ownerId) {
        super(ownerId, 135, "Yawgmoth's Agenda", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        this.expansionSetCode = "INV";

        // You can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantCastMoreThanOneSpellEffect(TargetController.YOU)));
        // You may play cards from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new YawgmothsAgendaCanPlayCardsFromGraveyardEffect()));
        // If a card would be put into your graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new YawgmothsAgendaReplacementEffect()));
    }

    public YawgmothsAgenda(final YawgmothsAgenda card) {
        super(card);
    }

    @Override
    public YawgmothsAgenda copy() {
        return new YawgmothsAgenda(this);
    }
}

class YawgmothsAgendaCanPlayCardsFromGraveyardEffect extends ContinuousEffectImpl {

    public YawgmothsAgendaCanPlayCardsFromGraveyardEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public YawgmothsAgendaCanPlayCardsFromGraveyardEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may play cards from your graveyard";
    }

    public YawgmothsAgendaCanPlayCardsFromGraveyardEffect(final YawgmothsAgendaCanPlayCardsFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public YawgmothsAgendaCanPlayCardsFromGraveyardEffect copy() {
        return new YawgmothsAgendaCanPlayCardsFromGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null)
                {
                    player.setPlayCardsFromGraveyard(true);
                }
            }
            return true;
        }
        return false;
    }

}

class YawgmothsAgendaReplacementEffect extends ReplacementEffectImpl {

    public YawgmothsAgendaReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "If a card would be put into your graveyard from anywhere, exile it instead";
    }

    public YawgmothsAgendaReplacementEffect(final YawgmothsAgendaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public YawgmothsAgendaReplacementEffect copy() {
        return new YawgmothsAgendaReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (permanent != null) {
                    return controller.moveCardToExileWithInfo(permanent, null, "", source.getSourceId(), game, ((ZoneChangeEvent) event).getFromZone(), true);
                }
            } else {
                Card card = game.getCard(event.getTargetId());
                if (card != null) {
                    return controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, ((ZoneChangeEvent) event).getFromZone(), true);
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.getOwnerId().equals(source.getControllerId())) {
                Permanent permanent = ((ZoneChangeEvent) event).getTarget();
                if (permanent == null || !(permanent instanceof PermanentToken)) {
                    return true;
                }
            }
        }
        return false;
    }

}
