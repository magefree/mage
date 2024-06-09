
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class RiotAbility extends SimpleStaticAbility {

    public RiotAbility() {
        super(Zone.ALL, new RiotReplacementEffect());
    }

    private RiotAbility(final RiotAbility ability) {
        super(ability);
    }

    @Override
    public RiotAbility copy() {
        return new RiotAbility(this);
    }

    @Override
    public String getRule() {
        return "Riot <i>(This creature enters the battlefield with your choice of a +1/+1 counter or haste.)</i>";
    }
}

class RiotReplacementEffect extends ReplacementEffectImpl {

    RiotReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
    }

    private RiotReplacementEffect(final RiotReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (creature != null && controller != null) {
            if (controller.chooseUse(outcome, "Have " + creature.getLogName() + " enter the battlefield with a +1/+1 counter on it or with haste?", null, "+1/+1 counter", "Haste", source, game)) {
                game.informPlayers(controller.getLogName() + " choose to put a +1/+1 counter on " + creature.getName());
                creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
            } else {
                game.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.Custom), source);
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return staticText;
    }

    @Override
    public RiotReplacementEffect copy() {
        return new RiotReplacementEffect(this);
    }

}
