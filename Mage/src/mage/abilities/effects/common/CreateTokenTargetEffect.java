package mage.abilities.effects.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.token.Token;

/**
 * @author Loki
 */
public class CreateTokenTargetEffect extends OneShotEffect<CreateTokenTargetEffect> {
    private Token token;
    private DynamicValue amount;

    public CreateTokenTargetEffect(Token token) {
        this(token, new StaticValue(1));
    }

    public CreateTokenTargetEffect(Token token, int amount) {
        this(token, new StaticValue(amount));
    }

    public CreateTokenTargetEffect(Token token, DynamicValue amount) {
        super(Constants.Outcome.PutCreatureInPlay);
        this.token = token;
        this.amount = amount.clone();
    }

    public CreateTokenTargetEffect(final CreateTokenTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.token = effect.token.copy();
    }

    @Override
    public CreateTokenTargetEffect copy() {
        return new CreateTokenTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = amount.calculate(game, source);
        token.putOntoBattlefield(value, game, source.getSourceId(), targetPointer.getFirst(game, source));
        return true;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder("put ");
        if (amount.toString().equals("1")) {
            sb.append("a");
        } else {
            sb.append(amount.toString());
        }
        sb.append(" ").append(token.getDescription()).append(" onto the battlefield");
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
