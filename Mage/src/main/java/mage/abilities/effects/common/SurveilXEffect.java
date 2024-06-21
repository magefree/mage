package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

public class SurveilXEffect extends OneShotEffect {

    protected final DynamicValue amount;

    public SurveilXEffect(int amount) {
        this(amount, "");
    }

    public SurveilXEffect(int amount, String message) {
        this(StaticValue.get(amount), message);
    }

    public SurveilXEffect(DynamicValue amount) {
        this(amount, "");
    }

    public SurveilXEffect(DynamicValue amount, String message) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.staticText = "surveil X" + (message.isEmpty() ? "" : ", ") + message;
    }

    private SurveilXEffect(final SurveilXEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public SurveilXEffect copy() {
        return new SurveilXEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = amount.calculate(game, source, this);
        return player.surveil(xValue, source, game);
    }
}