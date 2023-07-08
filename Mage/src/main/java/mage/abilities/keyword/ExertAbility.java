
package mage.abilities.keyword;

import java.util.HashSet;
import java.util.Set;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class ExertAbility extends SimpleStaticAbility {

    private String ruleText;

    public ExertAbility(BecomesExertSourceTriggeredAbility ability) {
        this(ability, false);
    }

    public ExertAbility(BecomesExertSourceTriggeredAbility ability, boolean exertOnlyOncePerTurn) {
        super(Zone.BATTLEFIELD, new ExertReplacementEffect(exertOnlyOncePerTurn));
        ruleText = (exertOnlyOncePerTurn
                ? "If {this} hasn't been exerted this turn, you may exert it"
                : "You may exert {this}") + " as it attacks. ";
        if (ability != null) {
            this.addSubAbility(ability);
            ruleText += "When you do,";
            ability.getEffects().forEach(effect -> {
                ruleText += " " + effect.getText(ability.getModes().getMode());
            });
            ruleText += ". ";
            ability.setRuleVisible(false);
        }
        ruleText += "<i>(An exerted creature won't untap during your next untap step.)</i>";
        if (exertOnlyOncePerTurn) {
            getWatchers().add(new ExertedThisTurnWatcher());
        }
    }

    public ExertAbility(final ExertAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;

    }

    @Override
    public ExertAbility copy() {
        return new ExertAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}

class ExertReplacementEffect extends ReplacementEffectImpl {

    private final boolean exertOnlyOncePerTurn;

    public ExertReplacementEffect(boolean exertOnlyOncePerTurn) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You may exert {this} as it attacks";
        this.exertOnlyOncePerTurn = exertOnlyOncePerTurn;
    }

    public ExertReplacementEffect(ExertReplacementEffect effect) {
        super(effect);
        this.exertOnlyOncePerTurn = effect.exertOnlyOncePerTurn;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(source.getSourceId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = game.getPermanent(event.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (creature != null && controller != null) {
            if (exertOnlyOncePerTurn) {
                MageObjectReference creatureReference = new MageObjectReference(creature.getId(), creature.getZoneChangeCounter(game), game);
                ExertedThisTurnWatcher watcher = game.getState().getWatcher(ExertedThisTurnWatcher.class);
                if (watcher != null && watcher.getExertedThisTurnCreatures().contains(creatureReference)) {
                    return false;
                }
            }
            if (controller.chooseUse(outcome, "Exert " + creature.getLogName() + '?',
                    "An exerted creature won't untap during your next untap step.", "Yes", "No", source, game)) {
                if (!game.isSimulation()) {
                    game.informPlayers(controller.getLogName() + " exerted " + creature.getName());
                }
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.BECOMES_EXERTED, creature.getId(), source, source.getControllerId()));
                ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("", creature.getControllerId());
                effect.setTargetPointer(new FixedTarget(creature, game));
                game.addEffect(effect, source);
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return staticText;
    }

    @Override
    public ExertReplacementEffect copy() {
        return new ExertReplacementEffect(this);
    }

}

class ExertedThisTurnWatcher extends Watcher {

    private final Set<MageObjectReference> exertedThisTurnCreatures;

    public ExertedThisTurnWatcher() {
        super(WatcherScope.GAME);
        exertedThisTurnCreatures = new HashSet<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BECOMES_EXERTED) {
            this.exertedThisTurnCreatures.add(new MageObjectReference(event.getTargetId(), game));
        }
    }

    public Set<MageObjectReference> getExertedThisTurnCreatures() {
        return this.exertedThisTurnCreatures;
    }

    @Override
    public void reset() {
        super.reset();
        exertedThisTurnCreatures.clear();
    }

}
