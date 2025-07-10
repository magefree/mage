package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class PlayLandOrCastSpellTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean fromExile;

    public PlayLandOrCastSpellTriggeredAbility(Effect effect) {
        this(effect, false, false);
    }

    public PlayLandOrCastSpellTriggeredAbility(Effect effect, boolean fromExile, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.fromExile = fromExile;
        setTriggerPhrase("Whenever you play a land" + (fromExile ? " from exile" : "") + " or cast a spell" + (fromExile ? " from exile" : "") + ", ");
    }

    private PlayLandOrCastSpellTriggeredAbility(final PlayLandOrCastSpellTriggeredAbility ability) {
        super(ability);
        this.fromExile = ability.fromExile;
    }

    @Override
    public PlayLandOrCastSpellTriggeredAbility copy() {
        return new PlayLandOrCastSpellTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId()) && (!fromExile || event.getZone() == Zone.EXILED);
    }
}
