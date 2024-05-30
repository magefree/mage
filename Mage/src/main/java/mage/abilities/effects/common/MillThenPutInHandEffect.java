package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class MillThenPutInHandEffect extends OneShotEffect {

    private final DynamicValue amount;
    private final boolean optional;
    private final int maxAmountReturned; // maximum number of cards returned. e.g. 2 for "up to two"
    private final FilterCard filter;
    private final Effect otherwiseEffect;
    private String textFromAmong = "the milled cards"; // for text gen

    /**
     * @param amount number of cards to mill
     * @param filter optionally select a card matching this filter from among the milled cards to put in hand
     */
    public MillThenPutInHandEffect(int amount, FilterCard filter) {
        this(amount, filter, true);
    }

    /**
     * @param amount   number of cards to mill
     * @param filter   select a card matching this filter from among the milled cards to put in hand
     * @param optional whether the selection is optional (true) or mandatory (false)
     */
    public MillThenPutInHandEffect(int amount, FilterCard filter, boolean optional) {
        this(amount, filter, null, optional);
    }

    /**
     * @param amount          number of cards to mill
     * @param filter          optionally select a card matching this filter from among the milled cards to put in hand
     * @param otherwiseEffect applied if no card put into hand
     */
    public MillThenPutInHandEffect(int amount, FilterCard filter, Effect otherwiseEffect) {
        this(amount, filter, otherwiseEffect, true);
        this.textFromAmong = "the cards milled this way";
    }

    public MillThenPutInHandEffect(int amount, FilterCard filter, Effect otherwiseEffect, boolean optional) {
        this(amount, filter, otherwiseEffect, optional, 1);
    }

    public MillThenPutInHandEffect(int amount, FilterCard filter, Effect otherwiseEffect, boolean optional, int maxReturnedCard) {
        this(StaticValue.get(amount), filter, otherwiseEffect, optional, maxReturnedCard);
    }

    public MillThenPutInHandEffect(DynamicValue amount, FilterCard filter) {
        this(amount, filter, true);
    }

    public MillThenPutInHandEffect(DynamicValue amount, FilterCard filter, boolean optional) {
        this(amount, filter, null, optional);
    }

    public MillThenPutInHandEffect(DynamicValue amount, FilterCard filter, Effect otherwiseEffect, boolean optional) {
        this(amount, filter, otherwiseEffect, optional, 1);
    }

    public MillThenPutInHandEffect(DynamicValue amount, FilterCard filter, Effect otherwiseEffect, boolean optional, int maxReturnedCard) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.filter = filter;
        this.optional = optional;
        this.maxAmountReturned = maxReturnedCard;
        this.otherwiseEffect = otherwiseEffect;
    }

    private MillThenPutInHandEffect(final MillThenPutInHandEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.optional = effect.optional;
        this.filter = effect.filter;
        this.maxAmountReturned = effect.maxAmountReturned;
        this.otherwiseEffect = effect.otherwiseEffect;
        this.textFromAmong = effect.textFromAmong;
    }

    @Override
    public MillThenPutInHandEffect copy() {
        return new MillThenPutInHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int toMill = amount.calculate(game, source, this);
        Cards cards = player.millCards(toMill, source, game);
        if (cards.isEmpty()) {
            return applyOtherwiseEffect(game, source);
        }
        TargetCard target = new TargetCard(optional ? 0 : maxAmountReturned, maxAmountReturned, Zone.ALL, filter);
        player.choose(Outcome.DrawCard, cards, target, source, game);
        Set<Card> returned = target
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (returned.isEmpty()) {
            return applyOtherwiseEffect(game, source);
        }
        return player.moveCards(returned, Zone.HAND, source, game);
    }

    private boolean applyOtherwiseEffect(Game game, Ability source) {
        if (otherwiseEffect instanceof OneShotEffect) {
            return otherwiseEffect.apply(game, source);
        } else if (otherwiseEffect instanceof ContinuousEffect) {
            game.addEffect((ContinuousEffect) otherwiseEffect, source);
            return true;
        } else {
            return false;
        }
    }

    public MillThenPutInHandEffect withTextOptions(String fromAmong) {
        this.textFromAmong = fromAmong;
        return this;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("mill ");
        String value = amount.toString();
        sb.append(CardUtil.numberToText(value, "a"));
        sb.append(value.equals("1") ? " card" : " cards");
        if (optional) {
            sb.append("You may put ");
        } else {
            sb.append((otherwiseEffect == null) ? "Then put " : "Put ");
        }
        if (maxAmountReturned > 1) {
            sb.append(optional ? "up to " : "");
            sb.append(CardUtil.numberToText(maxAmountReturned)).append(" ");
            sb.append(filter.getMessage());
        } else {
            sb.append(CardUtil.addArticle(filter.getMessage()));
        }
        sb.append(" from among ");
        sb.append(textFromAmong);
        sb.append(" into your hand");
        if (otherwiseEffect != null) {
            sb.append(". If you ");
            sb.append(optional ? "don't" : "can't");
            sb.append(", ");
            sb.append(otherwiseEffect.getText(mode));
        }
        return sb.toString();
    }
}
