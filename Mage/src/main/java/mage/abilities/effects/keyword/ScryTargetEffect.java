package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public class ScryTargetEffect extends OneShotEffect {

    protected final DynamicValue amount;

    public ScryTargetEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public ScryTargetEffect(DynamicValue amount) {
        super(Outcome.Benefit);
        this.amount = amount;
    }

    protected ScryTargetEffect(final ScryTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : getTargetPointer().getTargets(game, source)) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.canRespond()) {
                int toScry = amount.calculate(game, source, this);
                player.scry(toScry, source, game);
            }
        }
        return true;
    }

    @Override
    public ScryTargetEffect copy() {
        return new ScryTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder(getTargetPointer().describeTargets(mode.getTargets(), "that player"));
        sb.append(" scries ");
        sb.append(CardUtil.numberToText(amount.toString()));
        return sb.toString();
    }
}
