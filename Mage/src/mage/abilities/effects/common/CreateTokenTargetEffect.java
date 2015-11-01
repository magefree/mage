package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;

/**
 * @author Loki
 */
public class CreateTokenTargetEffect extends OneShotEffect {

    private Token token;
    private DynamicValue amount;
    private boolean tapped;
    private boolean attacking;

    public CreateTokenTargetEffect(Token token) {
        this(token, new StaticValue(1));
    }

    public CreateTokenTargetEffect(Token token, int amount) {
        this(token, new StaticValue(amount));
    }

    public CreateTokenTargetEffect(Token token, DynamicValue amount) {
        this(token, amount, false, false);
    }

    public CreateTokenTargetEffect(Token token, DynamicValue amount, boolean tapped, boolean attacking) {
        super(Outcome.PutCreatureInPlay);
        this.token = token;
        this.amount = amount.copy();
        this.tapped = tapped;
        this.attacking = attacking;
    }

    public CreateTokenTargetEffect(final CreateTokenTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.token = effect.token.copy();
        this.tapped = effect.tapped;
        this.attacking = effect.attacking;
    }

    @Override
    public CreateTokenTargetEffect copy() {
        return new CreateTokenTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = amount.calculate(game, source, this);
        if (value > 0) {
            return token.putOntoBattlefield(value, game, source.getSourceId(), targetPointer.getFirst(game, source), tapped, attacking);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("put ");
        sb.append(CardUtil.numberToText(amount.toString(), "a"));
        sb.append(" ").append(token.getDescription()).append(" onto the battlefield");
        if (tapped) {
            sb.append(" tapped");
        }
        if (attacking) {
            if (tapped) {
                sb.append(" and");
            }
            sb.append(" attacking");
        }
        String message = amount.getMessage();
        if (message.length() > 0) {
            sb.append(" for each ");
        }
        sb.append(message);
        sb.append(" under target ").append(mode.getTargets().get(0).getTargetName());
        sb.append("'s control");
        return sb.toString();
    }
}
