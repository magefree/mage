
package mage.abilities.effects;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class RequirementEffect extends ContinuousEffectImpl {

    boolean playerRelated; // defines a requirement that is more related to a player than a single creature

    public RequirementEffect(Duration duration) {
        this(duration, false);
    }

    /**
     * @param duration
     * @param playerRelated defines a requirement that is more related to a
     *                      player than a single creature
     */
    public RequirementEffect(Duration duration, boolean playerRelated) {
        super(duration, Outcome.Detriment);
        this.effectType = EffectType.REQUIREMENT;
        this.playerRelated = playerRelated;
    }

    protected RequirementEffect(final RequirementEffect effect) {
        super(effect);
        this.playerRelated = effect.playerRelated;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public abstract boolean applies(Permanent permanent, Ability source, Game game);

    public abstract boolean mustAttack(Game game);

    public abstract boolean mustBlock(Game game);

    public boolean mustBlockAny(Game game) {
        return false;
    }

    public boolean mustBlockAllAttackers(Game game) {
        return false;
    }

    /**
     * Defines the defender a attacker has to attack
     * TODO: WTF, it bugged, usage code need only players to attack, but effects return controller, source id, other broken data!!!
     *
     * @param source
     * @param game
     * @return
     */
    public UUID mustAttackDefender(Ability source, Game game) {
        return null;
    }

    /**
     * Defines the defenders an attacker has to attack, attacking any one of them obeys the requirement
     * (example: "attacks an opponent with the most life among your opponents" with tied opponents).
     * Override it instead of {@link #mustAttackDefender(Ability, Game)} when there can be more than one defender.
     *
     * @param source
     * @param game
     * @return defenders to choose from, empty if the requirement doesn't define a defender
     */
    public Set<UUID> mustAttackDefenders(Ability source, Game game) {
        UUID defenderId = mustAttackDefender(source, game);
        return defenderId == null ? Collections.emptySet() : Collections.singleton(defenderId);
    }

    public UUID mustBlockAttacker(Ability source, Game game) {
        return null;
    }

    public UUID mustBlockAttackerIfElseUnblocked(Ability source, Game game) {
        return null;
    }

    public int getMinNumberOfBlockers() {
        return 0;
    }

    /**
     * Player related check The player returned or controlled planeswalker must
     * be attacked with at least one attacker
     *
     * @param source
     * @param game
     * @return
     */
    public UUID playerMustBeAttackedIfAble(Ability source, Game game) {
        return null;
    }

    public boolean isPlayerRelated() {
        return playerRelated;
    }

}
