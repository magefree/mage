package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class SetPlayerLifeSourceEffect extends OneShotEffect {

    protected DynamicValue amount;

    public SetPlayerLifeSourceEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public SetPlayerLifeSourceEffect(DynamicValue amount) {
        super(Outcome.Neutral);
        this.amount = amount;
        this.staticText = setText();
    }

    protected SetPlayerLifeSourceEffect(final SetPlayerLifeSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public SetPlayerLifeSourceEffect copy() {
        return new SetPlayerLifeSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.setLife(amount.calculate(game, source, this), game, source);
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("your life total becomes ");
        sb.append(amount.toString());
        return sb.toString();
    }
}
