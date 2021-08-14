package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SuddenInsight extends CardImpl {

    public SuddenInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Draw a card for each different mana value among nonland cards in your graveyard.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(SuddenInsightValue.instance));
        this.getSpellAbility().addHint(SuddenInsightHint.instance);
    }

    private SuddenInsight(final SuddenInsight card) {
        super(card);
    }

    @Override
    public SuddenInsight copy() {
        return new SuddenInsight(this);
    }
}

enum SuddenInsightValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        return player == null ? 0 : player
                .getGraveyard()
                .getCards(StaticFilters.FILTER_CARD_NON_LAND, game)
                .stream()
                .map(MageObject::getManaValue)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public SuddenInsightValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "different mana value among nonland cards in your graveyard";
    }

    @Override
    public String toString() {
        return "1";
    }
}

enum SuddenInsightHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return null;
        }
        List<String> values = player
                .getGraveyard()
                .getCards(StaticFilters.FILTER_CARD_NON_LAND, game)
                .stream()
                .map(MageObject::getManaValue)
                .distinct()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.toList());
        return "Different mana values among nonland cards in your graveyard: " + values.size()
                + (values.size() > 0 ? " (" + String.join(", ", values) + ')' : "");
    }

    @Override
    public SuddenInsightHint copy() {
        return this;
    }
}
