package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
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

public class ExileTopXMayPlayUntilEffect extends OneShotEffect {

    private final DynamicValue amount;
    private final Duration duration;

    public ExileTopXMayPlayUntilEffect(int amount, Duration duration) {
        this(StaticValue.get(amount), duration);
    }

    public ExileTopXMayPlayUntilEffect(DynamicValue amount, Duration duration) {
        super(Outcome.Benefit);
        this.amount = amount.copy();
        this.duration = duration;
        makeText(amount.toString().equals("1") ? "that card" : "those cards", duration == Duration.EndOfTurn);
    }

    private ExileTopXMayPlayUntilEffect(final ExileTopXMayPlayUntilEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
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
        Set<Card> cards = controller.getLibrary().getTopCards(game, resolvedAmount);
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
        text += " of your library. ";
        if (durationRuleAtEnd) {
            text += "You may play " + refCardText + ' ' + (duration == Duration.EndOfTurn ? "this turn" : duration.toString());
        } else {
            text += CardUtil.getTextWithFirstCharUpperCase(duration.toString()) + ", you may play " + refCardText;
        }
        this.staticText = text;
    }

}
