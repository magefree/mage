package mage.abilities.effects;

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
public abstract class RestrictionEffect extends ContinuousEffectImpl {

    public RestrictionEffect(Duration duration) {
        this(duration, Outcome.Detriment);
    }

    public RestrictionEffect(Duration duration, Outcome outcome) {
        super(duration, outcome);
        this.effectType = EffectType.RESTRICTION;
    }

    public RestrictionEffect(final RestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public abstract boolean applies(Permanent permanent, Ability source, Game game);

    // canUseChooseDialogs -- restrict checks can be called by rules engine and by card info engine,
    // last one uses for info only and can't use dialogs, e.g. canUseChooseDialogs = false
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return true;
    }

    /**
     * @param attacker
     * @param defenderId id of planeswalker or player to attack, can be empty
     * for general checks
     * @param source
     * @param game
     * @param canUseChooseDialogs
     * @return
     */
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        return true;
    }

    public boolean canAttackCheckAfter(int numberOfAttackers, Ability source, Game game, boolean canUseChooseDialogs) {
        return true;
    }

    /**
     * @param attacker can be empty for general checks
     * @param blocker
     * @param source
     * @param game
     * @param canUseChooseDialogs
     * @return
     */
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return true;
    }

    public boolean canBlockCheckAfter(Ability source, Game game, boolean canUseChooseDialogs) {
        return true;
    }

    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return true;
    }

    /**
     * Called for all attackers after all blocking decisions are made
     *
     * @param attacker
     * @param source
     * @param game
     * @param canUseChooseDialogs
     * @return true = block is ok false = block is not valid (human: back to
     * defining blockers, AI: remove blocker)
     */
    public boolean canBeBlockedCheckAfter(Permanent attacker, Ability source, Game game, boolean canUseChooseDialogs) {
        return true;
    }

    public boolean canBeUntapped(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return true;
    }

    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return true;
    }

    public boolean canTransform(Game game, boolean canUseChooseDialogs) {
        return true;
    }
}
