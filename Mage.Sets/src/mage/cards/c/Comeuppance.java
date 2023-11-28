package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Comeuppance extends CardImpl {

    public Comeuppance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");


        // Prevent all damage that would be dealt to you and planeswalkers you control this turn by sources you don't control. If damage from a creature source is prevented this way, Comeuppance deals that much damage to that creature. If damage from a noncreature source is prevented this way, Comeuppance deals that much damage to the source's controller.
        this.getSpellAbility().addEffect(new ComeuppanceEffect());

    }

    private Comeuppance(final Comeuppance card) {
        super(card);
    }

    @Override
    public Comeuppance copy() {
        return new Comeuppance(this);
    }
}

class ComeuppanceEffect extends PreventionEffectImpl {

    public ComeuppanceEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        staticText = "Prevent all damage that would be dealt to you and planeswalkers you control this turn by sources you don't control. If damage from a creature source is prevented this way, {this} deals that much damage to that creature. If damage from a noncreature source is prevented this way, {this} deals that much damage to the source's controller";
    }

    private ComeuppanceEffect(final ComeuppanceEffect effect) {
        super(effect);
    }

    @Override
    public ComeuppanceEffect copy() {
        return new ComeuppanceEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        if (preventionData.getPreventedDamage() > 0) {
            MageObject damageDealingObject = game.getObject(event.getSourceId());
            UUID objectControllerId = null;
            if (damageDealingObject instanceof Permanent) {
                if (damageDealingObject.isCreature(game)) {
                    ((Permanent) damageDealingObject).damage(preventionData.getPreventedDamage(), source.getSourceId(), source, game);
                } else {
                    objectControllerId = ((Permanent) damageDealingObject).getControllerId();
                }
            } else if (damageDealingObject instanceof Ability) {
                objectControllerId = ((Ability) damageDealingObject).getControllerId();
            } else if (damageDealingObject instanceof Spell) {
                objectControllerId = ((Spell) damageDealingObject).getControllerId();
            }
            if (objectControllerId != null) {
                Player objectController = game.getPlayer(objectControllerId);
                if (objectController != null) {
                    objectController.damage(preventionData.getPreventedDamage(), source.getSourceId(), source, game);
                }
            }
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!super.applies(event, source, game)) {
            return false;
        }
        boolean catched = false;
        if (event.getTargetId().equals(source.getControllerId())) {
            catched = true;
        } else {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if (targetPermanent != null &&
                    targetPermanent.isControlledBy(source.getControllerId()) &&
                    targetPermanent.isPlaneswalker(game)) {
                catched = true;
            }
        }
        if (catched) {
            MageObject damageSource = game.getObject(event.getSourceId());
            if (damageSource instanceof StackObject) {
                return !((StackObject) damageSource).isControlledBy(source.getControllerId());
            } else if (damageSource instanceof Permanent) {
                return !((Permanent) damageSource).isControlledBy(source.getControllerId());
            } else if (damageSource instanceof Card) {
                return !((Card) damageSource).isOwnedBy(source.getControllerId());
            }
            Logger.getLogger(Comeuppance.class).error("Comeuppance: could not define source objects controller - " + (damageSource != null ? damageSource.getName() : "null"));
        }
        return false;
    }

}
