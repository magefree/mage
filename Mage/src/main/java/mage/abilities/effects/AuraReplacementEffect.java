package mage.abilities.effects;

import java.util.Locale;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import static org.apache.log4j.LogMF.info;

/**
 * Cards with the Aura subtype don't change the zone they are in, if there is no
 * valid target on the battlefield. Also, when entering the battlefield and it
 * was not cast (so from Zone != Hand), this effect gets the target to whitch to
 * attach it and adds the Aura the the battlefield and attachs it to the target.
 * The "attachTo:" value in game state has to be set therefore.
 * <p>
 * If no "attachTo:" value is defined, the controlling player has to chose the
 * aura target.
 * <p>
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
        if (card == null) {
            return false;
        }

        Card firstCardFace = null;
        if (game.getState().getValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + card.getId()) != null) {
            firstCardFace = card;
            card = card.getSecondCardFace();
            if (!card.isEnchantment(game) || !card.hasSubtype(SubType.AURA, game)) {
                return false;
            }
        }

        // Aura cards that go to battlefield face down (Manifest) don't have to select targets
        if (card.isFaceDown(game)) {
            return false;
        }
        // Aura enters the battlefield attached
        Object object = game.getState().getValue("attachTo:" + card.getId());
        if (object != null) {
            if (object instanceof Permanent) {
                // Aura is attached to a permanent on the battlefield
                return false;
            }
            if (object instanceof UUID) {
                Player player = game.getPlayer((UUID) object);
                if (player != null) {
                    // Aura is attached to a player
                    return false;
                }
            }
        }

        UUID targetId = null;
        MageObject sourceObject = game.getObject(event.getSourceId());
        boolean enchantCardInGraveyard = false;
        if (sourceObject instanceof StackAbility) {
            StackAbility stackAbility = (StackAbility) sourceObject;
            if (!stackAbility.getEffects().isEmpty()) {
                targetId = stackAbility.getEffects().get(0).getTargetPointer().getFirst(game, stackAbility);
            }
        }

        game.applyEffects(); // So continuousEffects are removed if previous effect of the same ability did move objects that cause continuous effects
        Player controllingPlayer = null;
        if (targetId == null) {
            SpellAbility spellAbility = card.getSpellAbility();
            if (spellAbility.getTargets().isEmpty()) {
                for (Ability ability : card.getAbilities(game)) {
                    if ((ability instanceof SpellAbility)
                            && SpellAbilityType.BASE_ALTERNATE == ((SpellAbility) ability).getSpellAbilityType()
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
                target.clearChosen(); // necessary if e.g. aura is blinked multiple times
            }

            if (event.getPlayerId() != null) {
                controllingPlayer = game.getPlayer(event.getPlayerId());
            } else {
                controllingPlayer = game.getPlayer(card.getOwnerId());
            }

            if (target != null && controllingPlayer != null && controllingPlayer.choose(auraOutcome, target, source, game)) {
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
            if (firstCardFace != null) {
                // transforming card. remove first face (original card) from old zone
                firstCardFace.removeFromZone(game, fromZone, source);
            } else {
                card.removeFromZone(game, fromZone, source);
            }
            PermanentCard permanent = new PermanentCard(card, (controllingPlayer == null ? card.getOwnerId() : controllingPlayer.getId()), game);
            ZoneChangeEvent zoneChangeEvent = new ZoneChangeEvent(permanent, event.getPlayerId(), fromZone, Zone.BATTLEFIELD);
            permanent.updateZoneChangeCounter(game, zoneChangeEvent);
            game.addPermanent(permanent, 0);
            card.setZone(Zone.BATTLEFIELD, game);
            if (permanent.entersBattlefield(source, game, fromZone, true)) {
                String attachToName = null;
                if (targetCard != null) {
                    permanent.attachTo(targetCard.getId(), source, game);
                    attachToName = targetCard.getLogName();
                } else if (targetPermanent != null) {
                    targetPermanent.addAttachment(permanent.getId(), source, game);
                    attachToName = targetPermanent.getLogName();
                } else if (targetPlayer != null) {
                    targetPlayer.addAttachment(permanent.getId(), source, game);
                    attachToName = targetPlayer.getLogName();
                }
                game.applyEffects();

                game.fireEvent(zoneChangeEvent);
                if (!game.isSimulation()) {
                    if (controllingPlayer != null && fromZone != null && permanent != null) {
                        game.informPlayers(controllingPlayer.getLogName() + " puts "
                                + (card.getLogName()) + " from "
                                + fromZone.toString().toLowerCase(Locale.ENGLISH) + " onto the Battlefield attached to "
                                + attachToName
                        );
                    }
                }
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
        if (((ZoneChangeEvent) event).getToZone() == Zone.BATTLEFIELD
                && (((ZoneChangeEvent) event).getFromZone() != Zone.STACK)) {
            Card card = game.getCard(event.getTargetId());
            return card != null && (card.isEnchantment(game) && card.hasSubtype(SubType.AURA, game)
                    || // in case of transformable enchantments
                    (game.getState().getValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + card.getId()) != null
                    && card.getSecondCardFace() != null
                    && card.getSecondCardFace().isEnchantment(game)
                    && card.getSecondCardFace().hasSubtype(SubType.AURA, game)));
        }
        return false;
    }
}
