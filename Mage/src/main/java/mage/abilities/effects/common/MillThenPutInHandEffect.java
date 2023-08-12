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
    private final FilterCard filter;
    private final Effect otherwiseEffect;

    public MillThenPutInHandEffect(int amount, FilterCard filter) {
        this(amount, filter, null);
    }

    public MillThenPutInHandEffect(int amount, FilterCard filter, Effect otherwiseEffect) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.filter = filter;
        this.otherwiseEffect = otherwiseEffect;
    }

    private MillThenPutInHandEffect(final MillThenPutInHandEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter;
        this.otherwiseEffect = effect.otherwiseEffect;
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
        TargetCard target = new TargetCard(0, 1, Zone.ALL, filter);
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

    @Override
    public String getText(Mode mode) {
        if (staticText == null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("mill ");
        sb.append(CardUtil.numberToText(amount));
        sb.append(" cards. You may put ");
        sb.append(filter.getMessage());
        sb.append(" from among the milled cards into your hand");
        if (otherwiseEffect != null) {
            sb.append(". If you don't, ");
            sb.append(otherwiseEffect.getText(mode));
        }
        return sb.toString();
    }
}
