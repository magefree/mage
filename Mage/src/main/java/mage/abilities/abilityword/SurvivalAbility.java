package mage.abilities.abilityword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Optional;

/**
 * TODO: This should only trigger on the second main phase, this is part of a larger refactor that has to be done
 *
 * @author TheElk801
 */
public class SurvivalAbility extends TriggeredAbilityImpl {

    public SurvivalAbility(Effect effect) {
        this(effect, false);
    }

    public SurvivalAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("At the beginning of your second main phase, if {this} is tapped, ");
        setAbilityWord(AbilityWord.SURVIVOR);
    }

    private SurvivalAbility(final SurvivalAbility ability) {
        super(ability);
    }

    @Override
    public SurvivalAbility copy() {
        return new SurvivalAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.POSTCOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(getControllerId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return Optional
                .ofNullable(getSourcePermanentIfItStillExists(game))
                .map(Permanent::isTapped)
                .orElse(false);
    }
}
