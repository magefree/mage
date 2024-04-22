
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author noxx
 */
public class LoseLifeDefendingPlayerEffect extends OneShotEffect {

    private DynamicValue amount;
    private boolean attackerIsSource;

    /**
     * @param amount
     * @param attackerIsSource true if the source.getSourceId() contains the
     *                         attacker false if attacker has to be taken from targetPointer
     */
    public LoseLifeDefendingPlayerEffect(int amount, boolean attackerIsSource) {
        this(StaticValue.get(amount), attackerIsSource);
    }

    public LoseLifeDefendingPlayerEffect(DynamicValue amount, boolean attackerIsSource) {
        super(Outcome.Damage);
        this.amount = amount;
        this.attackerIsSource = attackerIsSource;
    }

    protected LoseLifeDefendingPlayerEffect(final LoseLifeDefendingPlayerEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.attackerIsSource = effect.attackerIsSource;
    }

    @Override
    public LoseLifeDefendingPlayerEffect copy() {
        return new LoseLifeDefendingPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player defender;
        if (attackerIsSource) {
            defender = game.getPlayer(game.getCombat().getDefendingPlayerId(source.getSourceId(), game));
        } else {
            defender = game.getPlayer(getTargetPointer().getFirst(game, source));
        }
        if (defender != null) {
            defender.loseLife(amount.calculate(game, source, this), game, source, false);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "defending player loses " + amount + " life";
    }

}
