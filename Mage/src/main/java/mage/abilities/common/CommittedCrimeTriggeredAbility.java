package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.Ownerable;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class CommittedCrimeTriggeredAbility extends TriggeredAbilityImpl {

    public CommittedCrimeTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public CommittedCrimeTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever you commit a crime, ");
    }

    private CommittedCrimeTriggeredAbility(final CommittedCrimeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CommittedCrimeTriggeredAbility copy() {
        return new CommittedCrimeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_CAST:
            case ACTIVATED_ABILITY:
            case TRIGGERED_ABILITY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(getCriminal(event, game));
    }

    public static UUID getCriminal(GameEvent event, Game game) {
        UUID controllerId;
        Ability ability;
        switch (event.getType()) {
            case SPELL_CAST:
                Spell spell = game.getSpell(event.getTargetId());
                if (spell == null) {
                    return null;
                }
                controllerId = spell.getControllerId();
                ability = spell.getSpellAbility();
                break;
            case ACTIVATED_ABILITY:
            case TRIGGERED_ABILITY:
                StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
                if (stackObject == null) {
                    return null;
                }
                controllerId = stackObject.getControllerId();
                ability = stackObject.getStackAbility();
                break;
            default:
                return null;
        }
        if (controllerId == null || ability == null) {
            return null;
        }
        Set<UUID> opponents = game.getOpponents(controllerId);
        Set<UUID> targets = CardUtil.getAllSelectedTargets(ability, game);
        // an opponent
        if (targets
                .stream()
                .anyMatch(opponents::contains)
                // an opponent's permanent
                || targets
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .anyMatch(opponents::contains)
                // an opponent's spell or ability
                || targets
                .stream()
                .map(game.getStack()::getStackObject)
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .anyMatch(opponents::contains)
                // a card in an opponent's graveyard
                || targets
                .stream()
                .filter(uuid -> Zone.GRAVEYARD.match(game.getState().getZone(uuid)))
                .map(game::getCard)
                .filter(Objects::nonNull)
                .map(Ownerable::getOwnerId)
                .anyMatch(opponents::contains)) {
            return controllerId;
        }
        return null;
    }
}
