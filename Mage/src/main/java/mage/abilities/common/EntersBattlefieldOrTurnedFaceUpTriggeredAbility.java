package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.Optional;

/**
 * @author TheElk801
 */
public class EntersBattlefieldOrTurnedFaceUpTriggeredAbility extends TriggeredAbilityImpl {

    public EntersBattlefieldOrTurnedFaceUpTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public EntersBattlefieldOrTurnedFaceUpTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setWorksFaceDown(true);
        this.setTriggerPhrase("When {this} enters the battlefield or is turned face up, ");
    }

    private EntersBattlefieldOrTurnedFaceUpTriggeredAbility(final EntersBattlefieldOrTurnedFaceUpTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldOrTurnedFaceUpTriggeredAbility copy() {
        return new EntersBattlefieldOrTurnedFaceUpTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURNEDFACEUP
                || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        switch (event.getType()) {
            case TURNEDFACEUP:
                return true;
            case ENTERS_THE_BATTLEFIELD:
                Permanent sourcePermanent = getSourcePermanentIfItStillExists(game);
                return Optional
                        .ofNullable(getSourcePermanentIfItStillExists(game))
                        .filter(Objects::nonNull)
                        .map(permanent -> permanent.isFaceDown(game))
                        .orElse(false);
        }
        return false;
    }
}
