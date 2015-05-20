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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
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
public class YawgmothsWill extends CardImpl {

    public YawgmothsWill(UUID ownerId) {
        super(ownerId, 171, "Yawgmoth's Will", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{B}");
        this.expansionSetCode = "USG";


        // Until end of turn, you may play cards from your graveyard.
        this.getSpellAbility().addEffect(new CanPlayCardsFromGraveyardEffect());

        // If a card would be put into your graveyard from anywhere this turn, exile that card instead.
        this.getSpellAbility().addEffect(new YawgmothsWillReplacementEffect());
    }

    public YawgmothsWill(final YawgmothsWill card) {
        super(card);
    }

    @Override
    public YawgmothsWill copy() {
        return new YawgmothsWill(this);
    }
}

class CanPlayCardsFromGraveyardEffect extends ContinuousEffectImpl {

    public CanPlayCardsFromGraveyardEffect() {
        this(Duration.EndOfTurn);
    }

    public CanPlayCardsFromGraveyardEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Until end of turn, you may play cards from your graveyard";
    }

    public CanPlayCardsFromGraveyardEffect(final CanPlayCardsFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public CanPlayCardsFromGraveyardEffect copy() {
        return new CanPlayCardsFromGraveyardEffect(this);
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

class YawgmothsWillReplacementEffect extends ReplacementEffectImpl {

    public YawgmothsWillReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        this.staticText = "If a card would be put into your graveyard from anywhere this turn, exile that card instead";
    }

    public YawgmothsWillReplacementEffect(final YawgmothsWillReplacementEffect effect) {
        super(effect);
    }

    @Override
    public YawgmothsWillReplacementEffect copy() {
        return new YawgmothsWillReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
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
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
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
