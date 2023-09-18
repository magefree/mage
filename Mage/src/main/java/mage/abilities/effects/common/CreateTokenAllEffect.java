package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class CreateTokenAllEffect extends OneShotEffect {

    private final Token token;
    private final DynamicValue amount;
    private final TargetController targetController;
    private final boolean tapped;

    public CreateTokenAllEffect(Token token, TargetController targetController) {
        this(token, 1, targetController);
    }

    public CreateTokenAllEffect(Token token, int amount, TargetController targetController) {
        this(token, StaticValue.get(amount), targetController);
    }

    public CreateTokenAllEffect(Token token, DynamicValue amount, TargetController targetController) {
        this(token, amount, targetController, false);
    }

    public CreateTokenAllEffect(Token token, DynamicValue amount, TargetController targetController, boolean tapped) {
        super(Outcome.Benefit);
        this.token = token;
        this.tapped = tapped;
        this.amount = amount;
        this.targetController = targetController;
        this.setText();
    }

    private CreateTokenAllEffect(final CreateTokenAllEffect effect) {
        super(effect);
        this.token = effect.token;
        this.tapped = effect.tapped;
        this.amount = effect.amount;
        this.targetController = effect.targetController;
    }

    @Override
    public CreateTokenAllEffect copy() {
        return new CreateTokenAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = this.amount.calculate(game, source, this);
        for (UUID playerId : getPlayers(game, source)) {
            token.putOntoBattlefield(amount, game, source, playerId, tapped, false);
        }
        return true;
    }

    private Collection<UUID> getPlayers(Game game, Ability source) {
        switch (targetController) {
            case ANY:
            case EACH_PLAYER:
                return game.getState().getPlayersInRange(source.getControllerId(), game);
            case OPPONENT:
                return game.getOpponents(source.getControllerId());
        }
        return Collections.emptyList();
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("each ");
        switch (targetController) {
            case ANY:
            case EACH_PLAYER:
                sb.append("player");
                break;
            case OPPONENT:
                sb.append("opponent");
        }
        sb.append(" creates ");
        if (amount.toString().equals("1")) {
            if (tapped) {
                sb.append("a tapped ");
                sb.append(token.getDescription());
            } else {
                sb.append(CardUtil.addArticle(token.getDescription()));
            }
        } else {
            sb.append(CardUtil.numberToText(amount.toString())).append(' ');
            if (tapped) {
                sb.append("tapped ");
            }
            sb.append(token.getDescription().replace("token. It has", "tokens. They have"));
            if (token.getDescription().endsWith("token")) {
                sb.append("s");
            }
            int tokenLocation = sb.indexOf("token ");
            if (tokenLocation != -1) {
                sb.replace(tokenLocation, tokenLocation + 6, "tokens ");
            }
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

        staticText = sb.toString();
    }
}
