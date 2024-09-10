package mage.abilities.effects.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DrawCardTargetEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected boolean optional;
    protected boolean upTo;

    public DrawCardTargetEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public DrawCardTargetEffect(int amount, boolean optional) {
        this(StaticValue.get(amount), optional);
    }

    public DrawCardTargetEffect(DynamicValue amount) {
        this(amount, false);
    }

    public DrawCardTargetEffect(DynamicValue amount, boolean optional) {
        this(amount, optional, false);
    }

    public DrawCardTargetEffect(DynamicValue amount, boolean optional, boolean upto) {
        super(Outcome.DrawCard);
        this.amount = amount.copy();
        this.optional = optional;
        this.upTo = upto;
    }

    protected DrawCardTargetEffect(final DrawCardTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.optional = effect.optional;
        this.upTo = effect.upTo;
    }

    @Override
    public DrawCardTargetEffect copy() {
        return new DrawCardTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : getTargetPointer().getTargets(game, source)) {
            Player player = game.getPlayer(playerId);
            if (player != null
                    && player.canRespond()) {
                int cardsToDraw = amount.calculate(game, source, this);
                if (upTo) {
                    cardsToDraw = player.getAmount(0, cardsToDraw, "Draw how many cards?", game);
                }
                if (!optional
                        || player.chooseUse(outcome, "Use draw effect?", source, game)) {
                    player.drawCards(cardsToDraw, source, game);
                }
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder(getTargetPointer().describeTargets(mode.getTargets(), "that player"));
        if (optional) {
            sb.append(" may draw ");
        } else {
            sb.append(" draws ");
        }
        if (upTo) {
            sb.append("up to ");
        }
        sb.append(CardUtil.numberToText(amount.toString(), "a")).append(" card");
        try {
            if (Integer.parseInt(amount.toString()) > 1) {
                sb.append('s');
            }
        } catch (Exception e) {
            sb.append('s');
        }
        String message = amount.getMessage();
        if (!message.isEmpty()) {
            sb.append(" for each ");
        }
        sb.append(message);
        return sb.toString();
    }

}
