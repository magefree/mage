package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Set;

public class ExileTopXMayPlayUntilEndOfTurnEffect extends OneShotEffect {

    private final int amount;
    private final boolean showHint;
    private final Duration duration;

    public ExileTopXMayPlayUntilEndOfTurnEffect(int amount) {
        this(amount, false);
    }

    public ExileTopXMayPlayUntilEndOfTurnEffect(int amount, boolean showHint) {
        this(amount, showHint, Duration.EndOfTurn);
    }

    public ExileTopXMayPlayUntilEndOfTurnEffect(int amount, boolean showHint, Duration duration) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.showHint = showHint;
        this.duration = duration;
    }

    private ExileTopXMayPlayUntilEndOfTurnEffect(final ExileTopXMayPlayUntilEndOfTurnEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.showHint = effect.showHint;
        this.duration = effect.duration;
    }

    @Override
    public ExileTopXMayPlayUntilEndOfTurnEffect copy() {
        return new ExileTopXMayPlayUntilEndOfTurnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cards = controller.getLibrary().getTopCards(game, amount);
        if (cards.isEmpty()) {
            return true;
        }
        controller.moveCardsToExile(cards, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        // remove cards that could not be moved to exile
        cards.removeIf(card -> !Zone.EXILED.equals(game.getState().getZone(card.getId())));
        if (!cards.isEmpty()) {
            game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, duration)
                    .setTargetPointer(new FixedTargets(cards, game)), source);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (amount == 1) {
            sb.append("exile the top card of your library. ");
            sb.append(CardUtil.getTextWithFirstCharUpperCase(duration.toString()));
            sb.append(", you may play that card");
        } else {
            sb.append("exile the top ");
            sb.append(CardUtil.numberToText(amount));
            sb.append(" cards of your library. ");
            sb.append(CardUtil.getTextWithFirstCharUpperCase(duration.toString()));
            sb.append(", you may play cards exiled this way");
        }
        if (showHint) {
            sb.append(". <i>(You still pay its costs. You can play a land this way only if you have an available land play remaining.)</i>");
        }
        return sb.toString();
    }
}
