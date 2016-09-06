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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 * Cards with the Aura subtype don't change the zone they are in, if there is no
 * valid target on the battlefield. Also, when entering the battlefield and it
 * was not cast (so from Zone != Hand), this effect gets the target to whitch to
 * attach it and adds the Aura the the battlefield and attachs it to the target.
 * The "attachTo:" value in game state has to be set therefore.
 *
 * If no "attachTo:" value is defined, the controlling player has to chose the
 * aura target.
 *
 * This effect is automatically added to ContinuousEffects at the start of a
 * game
 *
 * @author North
 */
public class AuraReplacementEffect extends ReplacementEffectImpl {

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

        if (game.getState().getValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + card.getId()) != null) {
            card = card.getSecondCardFace();
        }

        // Aura cards that go to battlefield face down (Manifest) don't have to select targets
        if (card.isFaceDown(game)) {
            return false;
        }
        // Aura enters the battlefield attached
        Object object = game.getState().getValue("attachTo:" + card.getId());
        if (object != null && object instanceof PermanentCard) {
            return false;
        }

        UUID targetId = null;
        MageObject sourceObject = game.getObject(sourceId);
        boolean enchantCardInGraveyard = false;
//        if (sourceObject instanceof Spell) {
//            if (fromZone.equals(Zone.EXILED)) {
//                // cast from exile (e.g. Neightveil Spector) -> no replacement
//                return false;
//            }
//        }
        if (sourceObject instanceof StackAbility) {
            StackAbility stackAbility = (StackAbility) sourceObject;
            if (!stackAbility.getEffects().isEmpty()) {
                targetId = stackAbility.getEffects().get(0).getTargetPointer().getFirst(game, stackAbility);
            }
        }

        game.applyEffects(); // So continuousEffects are removed if previous effect of the same ability did move objects that cuase continuous effects
        Player controllingPlayer = null;
        if (targetId == null) {
            SpellAbility spellAbility = card.getSpellAbility();
            if (spellAbility.getTargets().isEmpty()) {
                for (Ability ability : card.getAbilities(game)) {
                    if ((ability instanceof SpellAbility)
                            && SpellAbilityType.BASE_ALTERNATE.equals(((SpellAbility) ability).getSpellAbilityType())
                            && !ability.getTargets().isEmpty()) {
                        spellAbility = (SpellAbility) ability;
                        break;
                    }
                }
            }
            if (spellAbility.getTargets().isEmpty()) {
                return false;
            }
            Target target = spellAbility.getTargets().get(0).copy();
            Outcome auraOutcome = Outcome.BoostCreature;
            for (Effect effect : spellAbility.getEffects()) {
                if (effect instanceof AttachEffect) {
                    auraOutcome = effect.getOutcome();
                    break;
                }
            }
            enchantCardInGraveyard = target instanceof TargetCardInGraveyard;
            if (target != null) {
                target.setNotTarget(true); // always not target because this way it's not handled targeted
                target.clearChosen(); // neccessary if e.g. aura is blinked multiple times
            }

            if (event.getPlayerId() != null) {
                controllingPlayer = game.getPlayer(event.getPlayerId());
            } else {
                controllingPlayer = game.getPlayer(card.getOwnerId());
            }

            if (target != null && controllingPlayer != null && controllingPlayer.choose(auraOutcome, target, card.getId(), game)) {
                targetId = target.getFirstTarget();
            }
        }

        Card targetCard = null;
        Permanent targetPermanent = null;
        if (enchantCardInGraveyard) {
            targetCard = game.getCard(targetId);
        } else {
            targetPermanent = game.getPermanent(targetId);
        }
        Player targetPlayer = game.getPlayer(targetId);
        if (targetCard != null || targetPermanent != null || targetPlayer != null) {
            card = game.getCard(event.getTargetId());
            card.removeFromZone(game, fromZone, sourceId);
            card.updateZoneChangeCounter(game);
            PermanentCard permanent = new PermanentCard(card, (controllingPlayer == null ? card.getOwnerId() : controllingPlayer.getId()), game);
            game.getBattlefield().addPermanent(permanent);
            card.setZone(Zone.BATTLEFIELD, game);
            if (permanent.entersBattlefield(event.getSourceId(), game, fromZone, true)) {
                if (targetCard != null) {
                    permanent.attachTo(targetCard.getId(), game);
                } else if (targetPermanent != null) {
                    targetPermanent.addAttachment(permanent.getId(), game);
                } else if (targetPlayer != null) {
                    targetPlayer.addAttachment(permanent.getId(), game);
                }
                game.applyEffects();

                game.fireEvent(new ZoneChangeEvent(permanent, controllerId, fromZone, Zone.BATTLEFIELD));
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getToZone().equals(Zone.BATTLEFIELD)
                && !(((ZoneChangeEvent) event).getFromZone().equals(Zone.STACK))) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && (card.getCardType().contains(CardType.ENCHANTMENT) && card.hasSubtype("Aura", game)
                    || // in case of transformable enchantments
                    (game.getState().getValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + card.getId()) != null
                    && card.getSecondCardFace() != null
                    && card.getSecondCardFace().getCardType().contains(CardType.ENCHANTMENT)
                    && card.getSecondCardFace().hasSubtype("Aura", game)))) {
                return true;
            }
        }
        return false;
    }
}
