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
import mage.target.Target;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author grimreap124
 */
public class MillThenPutOntoBattlefieldEffect extends OneShotEffect {

    private final DynamicValue amount;
    private final boolean optional;
    private final int maxAmountReturned; // maximum number of cards returned. e.g. 2 for "up to two"
    private final List<FilterCard> filters;
    private final Effect otherwiseEffect;
    private String textFromAmong = "the milled cards"; // for text gen

    /**
     * @param amount number of cards to mill
     * @param filter optionally select a card matching this filter from among the milled cards to put onto battlefield
     */
    public MillThenPutOntoBattlefieldEffect(int amount, FilterCard filter) {
        this(amount, filter, true);
    }

    public MillThenPutOntoBattlefieldEffect(int amount, TargetCard target) {
        this(amount, null, null, true, 1);
    }

    /**
     * @param amount   number of cards to mill
     * @param filter   select a card matching this filter from among the milled cards to put onto battlefield
     * @param optional whether the selection is optional (true) or mandatory (false)
     */
    public MillThenPutOntoBattlefieldEffect(int amount, FilterCard filter, boolean optional) {
        this(amount, filter, null, optional);
    }

    /**
     * @param amount          number of cards to mill
     * @param filter          optionally select a card matching this filter from among the milled cards to put onto battlefield
     * @param otherwiseEffect applied if no card put onto battlefield
     */
    public MillThenPutOntoBattlefieldEffect(int amount, FilterCard filter, Effect otherwiseEffect) {
        this(amount, filter, otherwiseEffect, true);
        this.textFromAmong = "the cards milled this way";
    }

    public MillThenPutOntoBattlefieldEffect(int amount, FilterCard filter, Effect otherwiseEffect, boolean optional) {
        this(amount, filter, otherwiseEffect, optional, 1);
    }

    public MillThenPutOntoBattlefieldEffect(int amount, FilterCard filter, Effect otherwiseEffect, boolean optional, int maxReturnedCard) {
        this(StaticValue.get(amount), filter, otherwiseEffect, optional, maxReturnedCard);
    }

    public MillThenPutOntoBattlefieldEffect(DynamicValue amount, FilterCard filter) {
        this(amount, filter, true);
    }

    public MillThenPutOntoBattlefieldEffect(DynamicValue amount, FilterCard filter, boolean optional) {
        this(amount, filter, null, optional);
    }

    public MillThenPutOntoBattlefieldEffect(DynamicValue amount, FilterCard filter, Effect otherwiseEffect, boolean optional) {
        this(amount, filter, otherwiseEffect, optional, 1);
    }

    public MillThenPutOntoBattlefieldEffect(DynamicValue amount, FilterCard filter, Effect otherwiseEffect, boolean optional, int maxReturnedCard) {
        this(amount, Stream.of(filter).collect(Collectors.toList()), otherwiseEffect, optional, maxReturnedCard);
    }

    public MillThenPutOntoBattlefieldEffect(DynamicValue amount, List<FilterCard> filters, Effect otherwiseEffect, boolean optional, int maxReturnedCard) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.filters = filters;
        this.optional = optional;
        this.maxAmountReturned = maxReturnedCard;
        this.otherwiseEffect = otherwiseEffect;
    }

    private MillThenPutOntoBattlefieldEffect(final MillThenPutOntoBattlefieldEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.optional = effect.optional;
        this.filters = effect.filters;
        this.maxAmountReturned = effect.maxAmountReturned;
        this.otherwiseEffect = effect.otherwiseEffect;
        this.textFromAmong = effect.textFromAmong;

    }

    @Override
    public MillThenPutOntoBattlefieldEffect copy() {
        return new MillThenPutOntoBattlefieldEffect(this);
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
        Set<Card> returned = new HashSet<Card>();
        for (FilterCard filter : filters) {
            TargetCard target =
                    new TargetCard(optional ? 0 : maxAmountReturned, maxAmountReturned, Zone.ALL, filter);
            player.choose(Outcome.Benefit, cards, target, source, game);
            returned.addAll(target
                    .getTargets()
                    .stream()
                    .map(game::getCard)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()));
            returned.forEach(cards::remove);
        }
        if (returned.isEmpty()) {
            return applyOtherwiseEffect(game, source);
        }
        return player.moveCards(returned, Zone.BATTLEFIELD, source, game);
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

    public MillThenPutOntoBattlefieldEffect withTextOptions(String fromAmong) {
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
        sb.append(value.equals("1") ? " card. " : " cards. ");
        if (optional) {
            sb.append("You may put ");
        } else {
            sb.append((otherwiseEffect == null) ? "Then put " : "Put ");
        }
        if (maxAmountReturned > 1) {
            sb.append(optional ? "up to " : "");
            sb.append(CardUtil.numberToText(maxAmountReturned)).append(" ");
            sb.append(filters.stream().map(FilterCard::getMessage).collect(Collectors.joining(" and/or ")));
        } else {
            sb.append(CardUtil.addArticle(filters.stream().map(FilterCard::getMessage).collect(Collectors.joining(" and/or "))));
        }
        sb.append(" from among ");
        sb.append(textFromAmong);
        sb.append(" onto the battlefield");
        if (otherwiseEffect != null) {
            sb.append(". If you ");
            sb.append(optional ? "don't" : "can't");
            sb.append(", ");
            sb.append(otherwiseEffect.getText(mode));
        }
        return sb.toString();
    }
}
