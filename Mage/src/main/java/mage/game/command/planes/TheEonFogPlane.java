package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.UntapAllEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Plane;
import mage.game.events.GameEvent;

/**
 * @author spjspj
 */
public class TheEonFogPlane extends Plane {

    public TheEonFogPlane() {
        this.setPlaneType(Planes.PLANE_THE_EON_FOG);

        // All players miss their untap step
        this.addAbility(new SimpleStaticAbility(Zone.COMMAND, new TheEonFogSkipUntapStepEffect()));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, untap all permanents you control
        this.addAbility(new ChaosEnsuesTriggeredAbility(new UntapAllEffect(StaticFilters.FILTER_CONTROLLED_PERMANENTS)));
    }

    private TheEonFogPlane(final TheEonFogPlane plane) {
        super(plane);
    }

    @Override
    public TheEonFogPlane copy() {
        return new TheEonFogPlane(this);
    }
}

class TheEonFogSkipUntapStepEffect extends ContinuousRuleModifyingEffectImpl {

    TheEonFogSkipUntapStepEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, false, false);
        staticText = "players skip their untap steps";
    }

    private TheEonFogSkipUntapStepEffect(final TheEonFogSkipUntapStepEffect effect) {
        super(effect);
    }

    @Override
    public TheEonFogSkipUntapStepEffect copy() {
        return new TheEonFogSkipUntapStepEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP_STEP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}
