package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class InvestigateTargetEffect extends OneShotEffect {

    private final DynamicValue amount;

    public InvestigateTargetEffect() {
        this(1);
    }

    public InvestigateTargetEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public InvestigateTargetEffect(DynamicValue amount) {
        super(Outcome.Benefit);
        this.amount = amount;
    }

    protected InvestigateTargetEffect(final InvestigateTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
            return false;
        }
        int value = this.amount.calculate(game, source, this);
        if (value > 0) {
            InvestigateEffect.doInvestigate(targetPlayer.getId(), value, game, source);
            return true;
        }
        return false;
    }

    @Override
    public InvestigateTargetEffect copy() {
        return new InvestigateTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "that player") + " investigates";
    }
}
