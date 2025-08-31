package mage.abilities.meta;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A triggered ability that combines several others and triggers whenever one or
 * more of them would. The abilities passed in should have null as their effect,
 * and should have their own targets set if necessary. All other information
 * will be passed in from changes to this Ability. Note: this does NOT work with
 * abilities that have intervening if clauses.
 *
 * @author noahg
 */
public class OrTriggeredAbility extends TriggeredAbilityImpl {

    private final String ruleTrigger;
    private final List<TriggeredAbility> triggeredAbilities = new ArrayList<>();

    public OrTriggeredAbility(Zone zone, Effect effect, TriggeredAbility... abilities) {
        this(zone, effect, false, null, abilities);
    }

    public OrTriggeredAbility(Zone zone, Effect effect, boolean optional, String ruleTrigger, TriggeredAbility... abilities) {
        super(zone, effect, optional);
        this.ruleTrigger = ruleTrigger;
        this.withRuleTextReplacement(false);
        Collections.addAll(this.triggeredAbilities, abilities);
        for (TriggeredAbility ability : triggeredAbilities) {
            //Remove useless data
            ability.getEffects().clear();

            for (Watcher watcher : ability.getWatchers()) {
                super.addWatcher(watcher);
            }

            if (ability.isLeavesTheBattlefieldTrigger()) {
                setLeavesTheBattlefieldTrigger(true);
            }
        }
        setTriggerPhrase(generateTriggerPhrase());

        // runtime check: enters and sacrifice must use Zone.ALL, see https://github.com/magefree/mage/issues/12826
        boolean haveEnters = false;
        boolean haveSacrifice = false;
        for (Ability ability : abilities) {
            if (ability.getRule().toLowerCase(Locale.ENGLISH).contains("enters")) {
                haveEnters = true;
            }
            if (ability.getRule().toLowerCase(Locale.ENGLISH).contains("sacrifice")) {
                haveSacrifice = true;
            }
        }
        if (zone != Zone.ALL && haveEnters && haveSacrifice) {
            throw new IllegalArgumentException("Wrong code usage: on enters and sacrifice OrTriggeredAbility must use Zone.ALL");
        }
    }

    public OrTriggeredAbility(OrTriggeredAbility ability) {
        super(ability);
        this.ruleTrigger = ability.ruleTrigger;
        for (TriggeredAbility triggeredAbility : ability.triggeredAbilities) {
            this.triggeredAbilities.add(triggeredAbility.copy());
        }
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        for (TriggeredAbility ability : triggeredAbilities) {
            if (ability.checkEventType(event, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean toRet = false;
        for (TriggeredAbility ability : triggeredAbilities) {
            for (Effect e : getEffects()) { // Add effects to the sub-abilities so that they can set target pointers
                ability.addEffect(e);
            }
            ability.getTargets().addAll(this.getTargets()); // AtStepTriggeredAbility automatically sets target pointer if it can't find any targets
            if (ability.checkEventType(event, game) && ability.checkTrigger(event, game) && ability.checkTriggerCondition(game)) {
                toRet = true;
            }
            ability.getTargets().clear();
            ability.getEffects().clear(); //Remove afterwards, ensures that they remain synced even with copying
        }
        return toRet;
    }

    @Override
    public OrTriggeredAbility copy() {
        return new OrTriggeredAbility(this);
    }

    private String generateTriggerPhrase() {
        if (ruleTrigger != null && !ruleTrigger.isEmpty()) {
            return ruleTrigger;
        }
        return triggeredAbilities
                .stream()
                .map(Ability::getRule)
                .map(CardUtil::getTextWithFirstCharLowerCase)
                .map(s -> s.substring(0, s.length() - 2))
                .collect(Collectors.joining(" or ")) + ", ";
    }

    @Override
    public void setControllerId(UUID controllerId) {
        super.setControllerId(controllerId);
        for (TriggeredAbility ability : triggeredAbilities) {
            ability.setControllerId(controllerId);
        }
    }

    @Override
    public void setSourceId(UUID sourceId) {
        super.setSourceId(sourceId);
        for (TriggeredAbility ability : triggeredAbilities) {
            ability.setSourceId(sourceId);
        }
    }

    @Override
    public void addWatcher(Watcher watcher) {
        super.addWatcher(watcher);
        for (TriggeredAbility ability : triggeredAbilities) {
            ability.addWatcher(watcher);
        }
    }

    @Override
    public List<Hint> getHints() {
        List<Hint> res = new ArrayList<>(super.getHints());
        this.triggeredAbilities.forEach(a -> res.addAll(a.getHints()));
        return res;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        boolean res = false;
        for (TriggeredAbility ability : triggeredAbilities) {
            // TODO: call full inner trigger instead like ability.isInUseableZone()?! Need research why it fails
            if (ability.isLeavesTheBattlefieldTrigger()) {
                // TODO: leaves battlefield and die are not same! Is it possible make a diff logic?
                res |= TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
            } else {
                res |= super.isInUseableZone(game, sourceObject, event);
            }
        }
        return res;
    }
}
