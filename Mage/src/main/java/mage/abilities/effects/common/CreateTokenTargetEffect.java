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
        this(token, StaticValue.get(1));
    }

    public CreateTokenTargetEffect(Token token, int amount) {
        this(token, StaticValue.get(amount));
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
        StringBuilder sb = new StringBuilder();

        if (mode.getTargets().isEmpty()) {
            sb.append("that player");
        }
        else {
            sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        }

        sb.append(" creates ");
        if (amount.toString().equals("1")) {
            sb.append("a ");
            if (tapped && !attacking) {
                sb.append("tapped ");
            }
            sb.append(token.getDescription());
        } else {
            sb.append(CardUtil.numberToText(amount.toString())).append(' ');
            if (tapped && !attacking) {
                sb.append("tapped ");
            }
            sb.append(token.getDescription());
            if (token.getDescription().endsWith("token")) {
                sb.append("s ");
            }
            int tokenLocation = sb.indexOf("token ");
            if (tokenLocation != -1) {
                sb.replace(tokenLocation, tokenLocation + 6, "tokens ");
            }
        }
        if (attacking) {
            sb.append(" that are");
            if (tapped) {
                sb.append(" tapped and");
            }
            sb.append(" attacking");
        }
        String message = amount.getMessage();
        if (!message.isEmpty()) {
            if (amount.toString().equals("X")) {
                sb.append(", where X is ");
            } else {
                sb.append(" for each ");
            }
        }
        sb.append(message);
        return sb.toString();
    }
}
