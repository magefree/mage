package mage.abilities.effects;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @param <T>
 * @author BetaSteward_at_googlemail.com
 */
public class ContinuousEffectsList<T extends ContinuousEffect> extends ArrayList<T> {

    private static final Logger logger = Logger.getLogger(ContinuousEffectsList.class);

    // the effectAbilityMap holds for each effect all abilities that are connected (used) with this effect
    private final Map<UUID, Set<Ability>> effectAbilityMap = new HashMap<>();

    public ContinuousEffectsList() {
    }

    public ContinuousEffectsList(final ContinuousEffectsList<T> effects) {
        this.ensureCapacity(effects.size());
        for (ContinuousEffect cost : effects) {
            this.add((T) cost.copy());
        }
        for (Map.Entry<UUID, Set<Ability>> entry : effects.effectAbilityMap.entrySet()) {
            Set<Ability> newSet = new HashSet<>();
            for (Ability ability : entry.getValue()) {
                newSet.add(ability.copy());
            }
            effectAbilityMap.put(entry.getKey(), newSet);
        }
    }

    public ContinuousEffectsList<T> copy() {
        return new ContinuousEffectsList<>(this);
    }

    public void removeEndOfTurnEffects(Game game) {
        // calls every turn on cleanup step (only end of turn duration)
        // rules 514.2
        for (Iterator<T> i = this.iterator(); i.hasNext(); ) {
            T entry = i.next();
            boolean canRemove = false;
            switch (entry.getDuration()) {
                case EndOfTurn:
                    canRemove = true;
                    break;
                case UntilEndOfYourNextTurn:
                    canRemove = entry.isYourNextTurn(game);
                    break;
            }
            if (canRemove) {
                i.remove();
                effectAbilityMap.remove(entry.getId());
            }
        }
    }

    public void removeEndOfCombatEffects() {

        for (Iterator<T> i = this.iterator(); i.hasNext(); ) {
            T entry = i.next();
            if (entry.getDuration() == Duration.EndOfCombat) {
                i.remove();
                effectAbilityMap.remove(entry.getId());
            }
        }
    }

    public void removeInactiveEffects(Game game) {
        for (Iterator<T> i = this.iterator(); i.hasNext(); ) {
            T entry = i.next();
            if (isInactive(entry, game)) {
                i.remove();
                effectAbilityMap.remove(entry.getId());
            }
        }
    }

    public void incYourTurnNumPlayed(Game game) {
        for (Iterator<T> i = this.iterator(); i.hasNext(); ) {
            T entry = i.next();
            if (game.isActivePlayer(entry.getStartingController())) {
                entry.incYourTurnNumPlayed();
            }
        }
    }

    private boolean isInactive(T effect, Game game) {
        // ends all inactive effects -- calls on player leave or apply new effect
        if (game.getState().isGameOver()) {
            // no need to remove effects after end -- users and tests must see last game state
            return false;
        }

        /*
        800.4a  When a player leaves the game, all objects (see rule 109) owned by that player leave the game and any effects
        which give that player control of any objects or players end. Then, if that player controlled any objects on the stack
        not represented by cards, those objects cease to exist. Then, if there are any objects still controlled by that player,
        those objects are exiled. This is not a state-based action. It happens as soon as the player leaves the game.
        If the player who left the game had priority at the time he or she left, priority passes to the next player in turn
        order who’s still in the game.
        */
        // objects removes doing in player.leave() call... effects removes is here

        Set<Ability> set = effectAbilityMap.get(effect.getId());
        if (set == null) {
            logger.debug("No abilities for effect found: " + effect.toString());
            return false;
        }
        Iterator<Ability> it = set.iterator();
        while (it.hasNext()) {
            Ability ability = it.next();
            if (ability == null) {
                it.remove();
            } else if (ability instanceof MageSingleton) {
                return false;
            } else if (effect.isDiscarded()) {
                it.remove();
            } else {
                // 800.4k  When a player leaves the game, any continuous effects with durations that last until that
                // player’s next turn or until a specific point in that turn will last until that turn would have begun.
                // They neither expire immediately nor last indefinitely.
                MageObject object = game.getObject(ability.getSourceId());
                boolean isObjectInGame = ability.getSourceId() == null || object != null; // Commander effects have no sourceId
                boolean isOwnerLeaveGame = false;
                if (object instanceof Card) {
                    Player owner = game.getPlayer(((Card) object).getOwnerId());
                    isOwnerLeaveGame = !owner.isInGame();
                }

                switch (effect.getDuration()) {
                    //
                    case WhileOnBattlefield:
                    case WhileInGraveyard:
                    case WhileOnStack:
                    case EndOfStep:
                    case EndOfCombat:
                    case EndOfGame:
                        // if the related source object does no longer exist in game - the effect has to be removed
                        if (isOwnerLeaveGame || !isObjectInGame) {
                            it.remove();
                        }
                        break;
                    case OneUse:
                        if (isOwnerLeaveGame || effect.isUsed()) {
                            it.remove();
                        }
                        break;
                    case Custom:
                        // custom effects must process it's own inactive method (override), but can'be missied by devs
                        if (isOwnerLeaveGame || effect.isInactive(ability, game)) {
                            it.remove();
                        }
                        break;
                    case EndOfTurn:
                        // end of turn discards on cleanup steps
                        // 514.2
                        break;
                    case UntilYourNextTurn:
                    case UntilEndOfYourNextTurn:
                        // until your turn effects continue until real turn reached, their used it's own inactive method
                        // 514.2 Second, the following actions happen simultaneously: all damage marked on permanents
                        // (including phased-out permanents) is removed and all "until end of turn" and "this turn" effects end.
                        // This turn-based action doesn’t use the stack.
                        if (effect.isInactive(ability, game)) {
                            it.remove();
                        }
                        break;
                    case UntilSourceLeavesBattlefield:
                        if (isOwnerLeaveGame || Zone.BATTLEFIELD != game.getState().getZone(ability.getSourceId())) {
                            it.remove();
                        }
                        break;
                    default:
                        throw new IllegalStateException("Effects gets unknown duration " + effect.getDuration() + ", effect: " + effect.toString());
                }
            }
        }
        return set.isEmpty();
    }

    /**
     * Adds an effect and its connected ability to the list. For each effect
     * will be stored, which abilities are connected to the effect. So an effect
     * can be connected to multiple abilities.
     *
     * @param effect - effect to add
     * @param source - connected ability
     */
    public void addEffect(T effect, Ability source) {
        if (effectAbilityMap.containsKey(effect.getId())) {
            Set<Ability> set = effectAbilityMap.get(effect.getId());
            for (Ability ability : set) {
                if (ability.getId().equals(source.getId()) && ability.getSourceId().equals(source.getSourceId())) {
                    return;
                }
            }
            set.add(source);
            return;
        }
        Set<Ability> set = new HashSet<>();
        set.add(source);
        this.effectAbilityMap.put(effect.getId(), set);
        this.add(effect);
    }

    public Set<Ability> getAbility(UUID effectId) {
        return effectAbilityMap.getOrDefault(effectId, new HashSet<>());
    }

    public void removeEffects(UUID effectIdToRemove, Set<Ability> abilitiesToRemove) {
        Set<Ability> abilities = effectAbilityMap.get(effectIdToRemove);
        if (abilitiesToRemove != null && abilities != null) {
            abilities.removeAll(abilitiesToRemove);
        }
        if (abilities == null || abilities.isEmpty()) {
            for (Iterator<T> iterator = this.iterator(); iterator.hasNext(); ) {
                ContinuousEffect effect = iterator.next();
                if (effect.getId().equals(effectIdToRemove)) {
                    iterator.remove();
                    break;
                }
            }
            effectAbilityMap.remove(effectIdToRemove);
        }
    }

    @Override
    public void clear() {
        super.clear();
        effectAbilityMap.clear();
    }
}
