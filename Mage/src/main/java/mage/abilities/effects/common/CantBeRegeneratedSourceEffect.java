
package mage.abilities.effects.common;

import java.util.Objects;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author jeffwadsworth
 */
public class CantBeRegeneratedSourceEffect extends ContinuousRuleModifyingEffectImpl {

    public CantBeRegeneratedSourceEffect(Duration duration) {
        super(duration, Outcome.Benefit, false, false);
        this.staticText = buildStaticText();
    }

    public CantBeRegeneratedSourceEffect(final CantBeRegeneratedSourceEffect effect) {
        super(effect);
    }

    @Override
    public CantBeRegeneratedSourceEffect copy() {
        return new CantBeRegeneratedSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.REGENERATE;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game); //To change body of generated methods, choose Tools | Templates.
        if (duration.isOnlyValidIfNoZoneChange()) {
            // If source permanent is no longer onto battlefield discard the effect
            if (source.getSourcePermanentIfItStillExists(game) == null) {
                discard();
            }
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return Objects.equals(source.getSourceId(), event.getTargetId());
    }

    private String buildStaticText() {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{this} can't be regenerated");
        if (!duration.toString().isEmpty()) {
            if (duration == Duration.EndOfTurn) {
                sb.append(" this turn");
            } else {
                sb.append(' ').append(duration.toString());
            }
        }
        return sb.toString();
    }

}
