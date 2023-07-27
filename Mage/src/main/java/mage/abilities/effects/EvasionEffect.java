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
     * Is there an hint to continuously display on the evasive permanent?
     * Note: all EvasionEffect do not want to display hint (keyworded ones for instance)
     */
    public boolean hasCantBeBlockedMessage(Permanent attacker, Ability source, Game game) {
        return null != this.staticCantBeBlockedMessage;
    }

    /**
     * If the effect prevents blocking, and there is a message to display in an hint on the evasive permanent,
     * returns the message.
     * <p>
     * Call hasCantBeBlockedMessage before-end to check if there is a message.
     */
    public String getCantBeBlockedMessage(Permanent attacker, Ability source, Game game) {
        if (this.staticCantBeBlockedMessage == null) {
            throw new UnsupportedOperationException("Message was requested while not defined.");
        }
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
