package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Set;

public class ExileTopXMayPlayUntilEffect extends OneShotEffect {

    private final DynamicValue amount;
    private final boolean chooseOne;
    private final Duration duration;

    public ExileTopXMayPlayUntilEffect(int amount, Duration duration) {
        this(amount, false, duration);
    }

    public ExileTopXMayPlayUntilEffect(int amount, boolean chooseOne, Duration duration) {
        this(StaticValue.get(amount), chooseOne, duration);
    }

    public ExileTopXMayPlayUntilEffect(DynamicValue amount, boolean chooseOne, Duration duration) {
        super(Outcome.Benefit);
        this.amount = amount.copy();
        this.chooseOne = chooseOne;
        this.duration = duration;
        makeText(amount.toString().equals("1") || chooseOne ? "that card" : "those cards", duration == Duration.EndOfTurn);
    }

    protected ExileTopXMayPlayUntilEffect(final ExileTopXMayPlayUntilEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.chooseOne = effect.chooseOne;
        this.duration = effect.duration;
    }

    @Override
    public ExileTopXMayPlayUntilEffect copy() {
        return new ExileTopXMayPlayUntilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int resolvedAmount = amount.calculate(game, source, this);
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, resolvedAmount));
        if (cards.isEmpty()) {
            return true;
        }
        controller.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        // remove cards that could not be moved to exile
        cards.retainZone(Zone.EXILED, game);
        if (chooseOne && cards.size() > 1) {
            TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
            target.withNotTarget(true);
            controller.choose(outcome, cards, target, source, game);
            cards.removeIf(uuid -> !uuid.equals(target.getFirstTarget()));
        }
        if (cards.isEmpty()) {
            return true;
        }
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, duration)
                .setTargetPointer(new FixedTargets(cards, game)), source);
        effectCards(game, source, cards.getCards(game));
        return true;
    }

    protected void effectCards(Game game, Ability source, Set<Card> cards) {
        //Do nothing, used for derived classes
    }

    /**
     * [Until end of turn, ] you may play [refCardText] [this turn]
     */
    public ExileTopXMayPlayUntilEffect withTextOptions(String refCardText, boolean durationRuleAtEnd) {
        makeText(refCardText, durationRuleAtEnd);
        return this;
    }

    private void makeText(String refCardText, boolean durationRuleAtEnd) {
        String text = "exile the top ";
        boolean singular = amount.toString().equals("1");
        text += singular ? "card" : CardUtil.numberToText(amount.toString()) + " cards";
        if (amount.toString().equals("X") && !amount.getMessage().isEmpty()) {
            text += " of your library, where X is " + amount.getMessage() + ". ";
        } else {
            text += " of your library. ";
        }
        if (chooseOne) {
            text += "Choose one of them. ";
        }
        if (durationRuleAtEnd) {
            text += "You may play " + refCardText + ' ' + (duration == Duration.EndOfTurn ? "this turn" : duration.toString());
        } else {
            text += CardUtil.getTextWithFirstCharUpperCase(duration.toString()) + ", you may play " + refCardText;
        }
        this.staticText = text;
    }
}
