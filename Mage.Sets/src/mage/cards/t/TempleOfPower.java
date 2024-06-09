package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class TempleOfPower extends CardImpl {

    public TempleOfPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.nightCard = true;

        // <i>(Transforms from Ojer Axonil, Deepest Might.)</i>

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {2}{R}, {T}: Transform Temple of Power. Activate only if red sources you controlled dealt 4 or more noncombat damage this turn and only as a sorcery.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new TransformSourceEffect(),
                new ManaCostsImpl<>("{2}{R}"),
                TempleOfPowerCondition.instance,
                TimingRule.SORCERY
        );
        ability.addWatcher(new TempleOfPowerWatcher());
        ability.addCost(new TapSourceCost());
        ability.addHint(TempleOfPowerHint.instance);
        this.addAbility(ability);
    }

    private TempleOfPower(final TempleOfPower card) {
        super(card);
    }

    @Override
    public TempleOfPower copy() {
        return new TempleOfPower(this);
    }
}

enum TempleOfPowerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        TempleOfPowerWatcher watcher = game.getState().getWatcher(TempleOfPowerWatcher.class);
        return watcher != null
                && 4 <= watcher.damageForPlayer(source.getControllerId());
    }

    @Override
    public String toString() {
        return "if red sources you controlled dealt 4 or more noncombat damage this turn";
    }
}

enum TempleOfPowerHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        TempleOfPowerWatcher watcher = game.getState().getWatcher(TempleOfPowerWatcher.class);
        if (watcher == null) {
            return "";
        }

        return "Non-combat damage from red source: "
                + watcher.damageForPlayer(ability.getControllerId());
    }

    @Override
    public TempleOfPowerHint copy() {
        return instance;
    }
}

class TempleOfPowerWatcher extends Watcher {

    // player -> total non combat damage from red source controlled by that player dealt this turn.
    private final Map<UUID, Integer> damageMap = new HashMap<>();

    public TempleOfPowerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT) {
            DamagedEvent dmgEvent = (DamagedEvent) event;

            // watch only non combat damage events.
            if (dmgEvent == null || dmgEvent.isCombatDamage()) {
                return;
            }

            MageObject sourceObject;
            UUID sourceControllerId = null;
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (sourcePermanent != null) {
                // source is a permanent.
                sourceObject = sourcePermanent;
                sourceControllerId = sourcePermanent.getControllerId();
            } else {
                sourceObject = game.getSpellOrLKIStack(event.getSourceId());
                if (sourceObject != null) {
                    // source is a spell.
                    sourceControllerId = ((StackObject) sourceObject).getControllerId();
                } else {
                    sourceObject = game.getObject(event.getSourceId());
                    if (sourceObject instanceof CommandObject) {
                        // source is a Command Object. For instance Emblem
                        sourceControllerId = ((CommandObject) sourceObject).getControllerId();
                    }
                }
            }

            // watch only red sources dealing damage
            if (sourceObject == null || sourceControllerId == null || !sourceObject.getColor().isRed()) {
                return;
            }

            damageMap.compute(sourceControllerId, (k, i) -> (i == null ? 0 : i) + event.getAmount());
        }
    }

    @Override
    public void reset() {
        damageMap.clear();
        super.reset();
    }

    int damageForPlayer(UUID playerId) {
        return damageMap.getOrDefault(playerId, 0);
    }
}
