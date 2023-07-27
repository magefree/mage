package mage.abilities.effects;

import mage.abilities.Ability;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Susucr
 */
public abstract class EvasionEffect extends ContinuousEffectImpl {

    public EvasionEffect(Duration duration) {
        this(duration, Outcome.Benefit);
    }

    protected String staticCantBeBlockedMessage;

    public EvasionEffect(Duration duration, Outcome outcome) {
        super(duration, outcome);
        this.effectType = EffectType.EVASION;
    }

    public EvasionEffect(Duration duration, Layer layer, SubLayer sublayer, Outcome outcome) {
        super(duration, layer, sublayer, outcome);
        this.effectType = EffectType.EVASION;
    }

    protected EvasionEffect(final EvasionEffect effect) {
        super(effect);
        this.staticCantBeBlockedMessage = effect.staticCantBeBlockedMessage;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public abstract boolean applies(Permanent permanent, Ability source, Game game);

    public boolean cantBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    /**
     * If the effect prevents blocking, this returns the message to display as an hint on the evasive permanent.
     */
    public String cantBeBlockedMessage(Permanent attacker, Ability source, Game game) {
        return this.staticCantBeBlockedMessage;
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
    public boolean cantBeBlockedCheckAfter(Permanent attacker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

}
