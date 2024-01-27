package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
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

/**
 * @author TheElk801
 */
public class MillThenPutInHandEffect extends OneShotEffect {

    private final int amount;
    private final boolean optional;
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
     * @param amount number of cards to mill
     * @param filter select a card matching this filter from among the milled cards to put in hand
     * @param optional whether the selection is optional (true) or mandatory (false)
     */
    public MillThenPutInHandEffect(int amount, FilterCard filter, boolean optional) {
        this(amount, filter, null, optional);
    }

    /**
     * @param amount number of cards to mill
     * @param filter optionally select a card matching this filter from among the milled cards to put in hand
     * @param otherwiseEffect applied if no card put into hand
     */
    public MillThenPutInHandEffect(int amount, FilterCard filter, Effect otherwiseEffect) {
        this(amount, filter, otherwiseEffect, true);
        this.textFromAmong = "the cards milled this way";
    }

    protected MillThenPutInHandEffect(int amount, FilterCard filter, Effect otherwiseEffect, boolean optional) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.filter = filter;
        this.optional = optional;
        this.otherwiseEffect = otherwiseEffect;
    }

    private MillThenPutInHandEffect(final MillThenPutInHandEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.optional = effect.optional;
        this.filter = effect.filter;
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
        Cards cards = player.millCards(amount, source, game);
        if (cards.isEmpty()) {
            return applyOtherwiseEffect(game, source);
        }
        TargetCard target = new TargetCard(optional ? 0 : 1, 1, Zone.ALL, filter);
        player.choose(Outcome.DrawCard, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return applyOtherwiseEffect(game, source);
        }
        return player.moveCards(card, Zone.HAND, source, game);
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
        String text = "mill " + CardUtil.numberToText(amount) + " cards. ";
        text += optional ? "You may " : "Then ";
        text += "put " + filter.getMessage() + " from among " + textFromAmong + " into your hand";
        if (otherwiseEffect != null) {
            text += ". If you don't, " + otherwiseEffect.getText(mode);
        }
        return text;
    }
}
