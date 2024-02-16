package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

//
//    702.96. Unleash
//
//    702.96a Unleash is a keyword that represents two static abilities.
//            "Unleash" means "You may have this permanent enter the battlefield with an additional +1/+1 counter on it"
//            and "This permanent can't block as long as it has a +1/+1 counter on it."
public class UnleashAbility extends SimpleStaticAbility {

    public UnleashAbility() {
        super(Zone.ALL, new UnleashReplacementEffect());
        this.addEffect(new UnleashRestrictionEffect());
    }

    protected UnleashAbility(final UnleashAbility ability) {
        super(ability);
    }

    @Override
    public UnleashAbility copy() {
        return new UnleashAbility(this);
    }

    @Override
    public String getRule() {
        return "Unleash <i>(You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)</i>";
    }
}

class UnleashReplacementEffect extends ReplacementEffectImpl {

    public UnleashReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
    }

    public UnleashReplacementEffect(UnleashReplacementEffect effect) {
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
            if (controller.chooseUse(outcome, "Unleash " + creature.getLogName() + '?', source, game)) {
                if (!game.isSimulation()) {
                    game.informPlayers(controller.getLogName() + " unleashes " + creature.getName());
                }
                creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return staticText;
    }

    @Override
    public UnleashReplacementEffect copy() {
        return new UnleashReplacementEffect(this);
    }

}

class UnleashRestrictionEffect extends RestrictionEffect {

    public UnleashRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
    }

    protected UnleashRestrictionEffect(final UnleashRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent != null && permanent.getId().equals(source.getSourceId())) {
            return permanent.getCounters(game).getCount(CounterType.P1P1) > 0;
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public UnleashRestrictionEffect copy() {
        return new UnleashRestrictionEffect(this);
    }
}
