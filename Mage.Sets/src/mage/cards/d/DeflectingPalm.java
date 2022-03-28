package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSource;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DeflectingPalm extends CardImpl {

    public DeflectingPalm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}");

        // The next time a source of your choice would deal damage to you this turn, prevent that damage. If damage is prevented this way, Deflecting Palm deals that much damage to that source's controller.
        this.getSpellAbility().addEffect(new DeflectingPalmEffect());
    }

    private DeflectingPalm(final DeflectingPalm card) {
        super(card);
    }

    @Override
    public DeflectingPalm copy() {
        return new DeflectingPalm(this);
    }
}

class DeflectingPalmEffect extends PreventionEffectImpl {

    private final TargetSource target;

    public DeflectingPalmEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        this.staticText = "The next time a source of your choice would deal damage to you this turn, prevent that damage. If damage is prevented this way, {this} deals that much damage to that source's controller";
        this.target = new TargetSource();
    }

    public DeflectingPalmEffect(final DeflectingPalmEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public DeflectingPalmEffect copy() {
        return new DeflectingPalmEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        super.init(source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        this.used = true;
        this.discard(); // only one use
        if (preventionData.getPreventedDamage() > 0) {
            MageObject damageDealingObject = game.getObject(target.getFirstTarget());
            UUID objectControllerId = null;
            if (damageDealingObject instanceof Permanent) {
                objectControllerId = ((Permanent) damageDealingObject).getControllerId();
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
        if (!this.used && super.applies(event, source, game)) {
            return event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(target.getFirstTarget());
        }
        return false;
    }
}
