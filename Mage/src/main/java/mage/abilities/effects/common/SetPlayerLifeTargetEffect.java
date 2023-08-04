
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Styxo
 */
public class SetPlayerLifeTargetEffect extends OneShotEffect {

    protected DynamicValue amount;

    public SetPlayerLifeTargetEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public SetPlayerLifeTargetEffect(DynamicValue amount) {
        super(Outcome.Neutral);
        this.amount = amount;
        this.staticText = setText();
    }

    protected SetPlayerLifeTargetEffect(final SetPlayerLifeTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public SetPlayerLifeTargetEffect copy() {
        return new SetPlayerLifeTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.setLife(amount.calculate(game, source, this), game, source);
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Target player's life total becomes ");
        sb.append(amount.toString());
        return sb.toString();
    }
}