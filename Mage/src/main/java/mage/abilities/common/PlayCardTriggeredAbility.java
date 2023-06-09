package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

// Author: alexander-novo
// A triggered ability for cards which say "whenever <someone> play(s) a card..."
public class PlayCardTriggeredAbility extends TriggeredAbilityImpl {

    private final TargetController targetController;

    /**
     * 
     * @param targetController Which player(s) playing cards can trigger this ability. Only [ANY, NOT_YOU, OPPONENT, YOU] are supported.
     * @param zone
     * @param effect
     */
    public PlayCardTriggeredAbility(TargetController targetController, Zone zone, Effect effect) {
        this(targetController, zone, effect, false);
    }

    /**
     * 
     * @param targetController Which player(s) playing cards can trigger this ability. Only [ANY, NOT_YOU, OPPONENT, YOU] are supported.
     * @param zone
     * @param effect
     * @param optional
     */
    public PlayCardTriggeredAbility(TargetController targetController, Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        this.targetController = targetController;

        constructTriggerPhrase();
    }

    private void constructTriggerPhrase() {
        switch (targetController) {
            case ANY:
                setTriggerPhrase("Whenever a player plays play a card, ");
                break;
            case NOT_YOU:
                setTriggerPhrase("Whenever another player plays a card, ");
                break;
            case OPPONENT:
                setTriggerPhrase("Whenever an opponent plays a card, ");
                break;
            case YOU:
                setTriggerPhrase("Whenever you play a card, ");
                break;
            default:
                throw new UnsupportedOperationException("TargetController not supported");
        }
    }

    public PlayCardTriggeredAbility(final PlayCardTriggeredAbility ability) {
        super(ability);

        this.targetController = ability.targetController;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean playerMatches;
        switch (targetController) {
            case ANY:
                playerMatches = true;
                break;
            case NOT_YOU:
                playerMatches = !isControlledBy(event.getPlayerId());
                break;
            case OPPONENT:
                playerMatches = game.getPlayer(getControllerId()).hasOpponent(event.getPlayerId(), game);
                break;
            case YOU:
                playerMatches = isControlledBy(event.getPlayerId());
                break;
            default:
                throw new UnsupportedOperationException("TargetController not supported");
        }

        // Make sure that, if a spell was cast, it came from an actual card (and not a copy of a card)
        return playerMatches && (event.getType() != GameEvent.EventType.SPELL_CAST
                || !game.getSpell(event.getTargetId()).getCard().isCopy());
    }

    @Override
    public PlayCardTriggeredAbility copy() {
        return new PlayCardTriggeredAbility(this);
    }

}
