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
package mage.abilities.effects;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author North
 */
public class AuraReplacementEffect extends ReplacementEffectImpl<AuraReplacementEffect> {

    public AuraReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Neutral);
    }

    public AuraReplacementEffect(final AuraReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AuraReplacementEffect copy() {
        return new AuraReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Zone fromZone = ((ZoneChangeEvent) event).getFromZone();
        Card card = game.getCard(event.getTargetId());
        UUID sourceId = event.getSourceId();
        UUID controllerId = event.getPlayerId();

        UUID targetId = null;
        MageObject sourceObject = game.getObject(sourceId);
        if (sourceObject instanceof StackAbility) {
            StackAbility stackAbility = (StackAbility) sourceObject;
            if (!stackAbility.getEffects().isEmpty()) {
                targetId = stackAbility.getEffects().get(0).getTargetPointer().getFirst(stackAbility);
            }
        }
        if (targetId == null) {
            Target target = card.getSpellAbility().getTargets().get(0);
            Player player = game.getPlayer(card.getOwnerId());
            if (player != null && player.choose(Outcome.BoostCreature, target, sourceId, game)) {
                targetId = target.getFirstTarget();
            }
        }

        Permanent targetPermanent = game.getPermanent(targetId);
        Player targetPlayer = game.getPlayer(targetId);
        if (targetPermanent != null || targetPlayer != null) {
            switch (fromZone) {
                case GRAVEYARD:
                    game.getPlayer(card.getOwnerId()).removeFromGraveyard(card, game);
                    break;
                case HAND:
                    game.getPlayer(card.getOwnerId()).removeFromHand(card, game);
                    break;
                case LIBRARY:
                    game.getPlayer(card.getOwnerId()).removeFromLibrary(card, game);
                    break;
                default:
            }
            game.rememberLKI(card.getId(), fromZone, card);

            PermanentCard permanent = new PermanentCard(card, card.getOwnerId());
            game.getBattlefield().addPermanent(permanent);
            game.setZone(card.getId(), Zone.BATTLEFIELD);
            game.applyEffects();
            permanent.entersBattlefield(event.getSourceId(), game);
            game.fireEvent(new ZoneChangeEvent(permanent, controllerId, fromZone, Zone.BATTLEFIELD));

            if (targetPermanent != null) {
                targetPermanent.addAttachment(permanent.getId(), game);
            }
            if (targetPlayer != null) {
                targetPlayer.addAttachment(permanent.getId(), game);
            }
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone() == Zone.BATTLEFIELD
                && ((ZoneChangeEvent) event).getFromZone() != Zone.HAND) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.getCardType().contains(CardType.ENCHANTMENT) && card.hasSubtype("Aura")) {
                return true;
            }
        }
        return false;
    }
}
