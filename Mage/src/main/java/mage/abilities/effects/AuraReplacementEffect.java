package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.cards.Card;
import mage.cards.DoubleFacedCardHalf;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.Locale;
import java.util.UUID;

/**
 * Cards with the Aura subtype don't change the zone they are in, if there is no
 * valid target on the battlefield. Also, when entering the battlefield and it
 * was not cast (so from Zone != Stack), this effect gets the target to which to
 * attach it and adds the Aura the the battlefield and attachs it to the target.
 * The "attachTo" value has to be set therefore (AttachEffect.setAttachTo).
 * <p>
 * If no "attachTo" value is defined, the controlling player has to chose the
 * aura target.
 * <p>
 * This effect is automatically added to ContinuousEffects at the start of a
 * game
 *
 * @author North
 */
public class AuraReplacementEffect extends ReplacementEffectImpl {

    private static final String VALUE_KEY_ENTER_AS_NON_AURA = "EnterAsNonAura";

    public AuraReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Neutral);
    }

    protected AuraReplacementEffect(final AuraReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AuraReplacementEffect copy() {
        return new AuraReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Zone fromZone = ((ZoneChangeEvent) event).getFromZone();
        Card card = game.getCard(event.getTargetId());
        if (card == null) {
            return false;
        }

        // Aura cards that go to battlefield face down (Manifest) don't have to select targets
        if (card.isFaceDown(game)) {
            return false;
        }
        // Aura enters the battlefield attached
        Object object = AttachEffect.pollAttachTo(game, card.getId());
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
        boolean enchantCardInGraveyard = false;

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
                target.withChooseHint("to attach " + card.getName() + " to");
                target.withNotTarget(true); // always not target because this way it's not handled targeted
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
            card.removeFromZone(game, fromZone, source);
            PermanentCard permanent = new PermanentCard(card, (controllingPlayer == null ? card.getOwnerId() : controllingPlayer.getId()), game);
            ZoneChangeEvent zoneChangeEvent = new ZoneChangeEvent(permanent, source, event.getPlayerId(), fromZone, Zone.BATTLEFIELD);
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
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() != Zone.BATTLEFIELD || zEvent.getFromZone() == Zone.STACK) {
            return false;
        }
        Card card = game.getCard(zEvent.getTargetId());
        if (card == null) {
            return false;
        }
        return findEnteringAura(game, card, card.isFaceDown(game)) != null;
    }

    /**
     * Mark a card to enter the battlefield as a non-Aura permanent (example: Takklemaggot), so the Aura's
     * enter rules (303.4f, 303.4g) are not used for it (e.g. no target choose). 
     * Must be called before the card's move.
     */
    public static void setEnterAsNonAura(Game game, Card card) {
        Card mainCard = card.getMainCard();
        game.getState().setValue(VALUE_KEY_ENTER_AS_NON_AURA + mainCard.getId() + mainCard.getZoneChangeCounter(game), Boolean.TRUE);
    }

    /**
     * Find the Aura that will enter the battlefield - the card's characteristics as it will exist on the battlefield
     * (side of a double faced card, transformed, face down, non-Aura).
     * Must be called before the card's move.
     *
     * @param faceDown the card enters the battlefield face down
     * @return the card's side that enters the battlefield as an Aura or null if it doesn't enter as an Aura
     */
    public static Card findEnteringAura(Game game, Card card, boolean faceDown) {
        // as face down
        // 708.2. face down permanent has no name, types, subtypes
        if (faceDown) {
            return null;
        }

        // as non-aura
        Card mainCard = card.getMainCard();
        if (Boolean.TRUE.equals(game.getState().getValue(VALUE_KEY_ENTER_AS_NON_AURA + mainCard.getId() + mainCard.getZoneChangeCounter(game)))) {
            return null;
        }

        // as transformed (dfc)
        Card enteringCard;
        boolean enterTransformed = Boolean.TRUE.equals(game.getState().getValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + card.getId() + card.getZoneChangeCounter(game)));
        if (enterTransformed && card instanceof DoubleFacedCardHalf) {
            // side of a transformable card enters with its back face up, same as in ZonesHandler
            enteringCard = ((DoubleFacedCardHalf) card).isBackSide() ? card : ((DoubleFacedCardHalf) card).getOtherSide();
        } else if (enterTransformed && card.getSecondCardFace() != null) {
            // main card of a transformable card
            enteringCard = card.getSecondCardFace();
        } else {
            // double faced cards: a main card has no own characteristics, must check the side that enters the battlefield
            enteringCard = CardUtil.getDefaultCardSideForBattlefield(game, card);
        }

        // as normal aura
        if (enteringCard.isEnchantment(game) && enteringCard.hasSubtype(SubType.AURA, game)) {
            return enteringCard;
        }
        return null;
    }
}
