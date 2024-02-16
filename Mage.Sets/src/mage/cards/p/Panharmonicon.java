
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;

/**
 *
 * @author emerald000
 */
public final class Panharmonicon extends CardImpl {

    public Panharmonicon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PanharmoniconEffect()));
    }

    private Panharmonicon(final Panharmonicon card) {
        super(card);
    }

    @Override
    public Panharmonicon copy() {
        return new Panharmonicon(this);
    }
}

class PanharmoniconEffect extends ReplacementEffectImpl {

    PanharmoniconEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time";
    }

    private PanharmoniconEffect(final PanharmoniconEffect effect) {
        super(effect);
    }

    @Override
    public PanharmoniconEffect copy() {
        return new PanharmoniconEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof NumberOfTriggersEvent) {
            NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
            // Only triggers of the controller of Panharmonicon
            if (source.isControlledBy(event.getPlayerId())) {
                GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
                // Only EtB triggers
                if (sourceEvent != null
                        && sourceEvent.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                        && sourceEvent instanceof EntersTheBattlefieldEvent) {
                    EntersTheBattlefieldEvent entersTheBattlefieldEvent = (EntersTheBattlefieldEvent) sourceEvent;
                    // Only for entering artifacts or creatures
                    if (entersTheBattlefieldEvent.getTarget().isArtifact(game)
                            || entersTheBattlefieldEvent.getTarget().isCreature(game)) {
                        // Only for triggers of permanents
                        if (game.getPermanent(numberOfTriggersEvent.getSourceId()) != null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
